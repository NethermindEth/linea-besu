/*
 * Copyright ConsenSys AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package org.hyperledger.besu.ethereum.core.json;

import org.hyperledger.besu.datatypes.Address;
import org.hyperledger.besu.datatypes.Hash;
import org.hyperledger.besu.ethereum.core.ExecutableData;
import org.hyperledger.besu.ethereum.core.Transaction;

import java.math.BigInteger;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.apache.tuweni.bytes.Bytes;
import org.apache.tuweni.units.bigints.UInt64;

public class ExecutableDataDeserializer extends StdDeserializer<ExecutableData> {

  public ExecutableDataDeserializer() {
    this(null);
  }

  public ExecutableDataDeserializer(final Class<?> vc) {
    super(vc);
  }

  @Override
  public ExecutableData deserialize(
      final JsonParser parser, final DeserializationContext deserializer)
      throws java.io.IOException {
    ExecutableData executableData = new ExecutableData();
    ObjectCodec codec = parser.getCodec();
    JsonNode node = codec.readTree(parser);

    executableData.parentHash = Hash.fromHexString(node.get("parent_hash").asText());
    executableData.feeRecipient = Address.fromHexString(node.get("fee_recipient").asText());
    executableData.stateRoot = Hash.fromHexString(node.get("state_root").asText());
    executableData.receiptsRoot = Hash.fromHexString(node.get("receipts_root").asText());
    executableData.logsBloom = Bytes.fromHexString(node.get("logs_bloom").asText());
    executableData.prevRandao = Hash.fromHexString(node.get("prev_randao").asText());
    executableData.number = UInt64.valueOf(node.get("block_number").asLong());
    executableData.gasLimit = UInt64.valueOf(node.get("gas_limit").asLong());
    executableData.gasUsed = UInt64.valueOf(node.get("gas_used").asLong());
    executableData.timestamp = UInt64.valueOf(node.get("timestamp").asLong());
    executableData.extraData = Bytes.fromHexString(node.get("extra_data").asText());
    executableData.baseFeePerGas = new BigInteger(node.get("base_fee_per_gas").asText());
    executableData.blockHash = Hash.fromHexString(node.get("block_hash").asText());

    JsonNode transactionsList = node.get("transactions");
    int numTxs = transactionsList.size();
    Transaction[] transactions = new Transaction[numTxs];
    for (int i = 0; i < numTxs; i++) {
      transactions[i] = Transaction.readFrom(Bytes.fromHexString(transactionsList.get(i).asText()));
    }
    executableData.transactions = transactions;
    return executableData;
  }
}
