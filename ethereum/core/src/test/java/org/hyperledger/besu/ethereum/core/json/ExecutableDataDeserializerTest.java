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

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.hyperledger.besu.datatypes.Address;
import org.hyperledger.besu.datatypes.Hash;
import org.hyperledger.besu.ethereum.core.ExecutableData;
import org.hyperledger.besu.ethereum.core.Transaction;

import java.math.BigInteger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tuweni.bytes.Bytes;
import org.apache.tuweni.units.bigints.UInt64;
import org.junit.jupiter.api.Test;

class ExecutableDataDeserializerTest {

  @Test
  public void deserialize() throws JsonProcessingException {
    String json =
        """
                 {
                         "parent_hash": "0xcf8e0d4e9587369b2301d0790347320302cc0943d5a1884560367e8208d920f2",
                         "fee_recipient": "0xabcf8e0d4e9587369b2301d0790347320302cc09",
                         "state_root": "0xcf8e0d4e9587369b2301d0790347320302cc0943d5a1884560367e8208d920f2",
                         "receipts_root": "0xcf8e0d4e9587369b2301d0790347320302cc0943d5a1884560367e8208d920f2",
                         "logs_bloom": "0x00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
                         "prev_randao": "0xcf8e0d4e9587369b2301d0790347320302cc0943d5a1884560367e8208d920f2",
                         "block_number": "1",
                         "gas_limit": "1",
                         "gas_used": "1",
                         "timestamp": "1",
                         "extra_data": "0xcf8e0d4e9587369b2301d0790347320302cc0943d5a1884560367e8208d920f2",
                         "base_fee_per_gas": "1",
                         "block_hash": "0xcf8e0d4e9587369b2301d0790347320302cc0943d5a1884560367e8208d920f2",
                         "transactions": [
                            "0xf86103018207d094b94f5374fce5edbc8e2a8697c15331677e6ebf0b0a8255441ca098ff921201554726367d2be8c804a7ff89ccf285ebc57dff8ae4c44b9c19ac4aa08887321be575c8095f789dd4c743dfe42c1820f9231f98a962b210e3ac2452a3"
                         ]
                }
                 """;
    ExecutableData executableData = new ObjectMapper().readValue(json, ExecutableData.class);
    assertEquals(
        executableData.parentHash,
        Hash.fromHexString("0xcf8e0d4e9587369b2301d0790347320302cc0943d5a1884560367e8208d920f2"));
    assertEquals(
        executableData.feeRecipient,
        Address.fromHexString("0xabcf8e0d4e9587369b2301d0790347320302cc09"));
    assertEquals(
        executableData.stateRoot,
        Hash.fromHexString("0xcf8e0d4e9587369b2301d0790347320302cc0943d5a1884560367e8208d920f2"));
    assertEquals(
        executableData.receiptsRoot,
        Hash.fromHexString("0xcf8e0d4e9587369b2301d0790347320302cc0943d5a1884560367e8208d920f2"));
    assertEquals(
        executableData.logsBloom,
        Bytes.fromHexString(
            "0x00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"));
    assertEquals(
        executableData.prevRandao,
        Hash.fromHexString("0xcf8e0d4e9587369b2301d0790347320302cc0943d5a1884560367e8208d920f2"));
    assertEquals(executableData.number, UInt64.valueOf(1));
    assertEquals(executableData.gasLimit, UInt64.valueOf(1));
    assertEquals(executableData.gasUsed, UInt64.valueOf(1));
    assertEquals(executableData.timestamp, UInt64.valueOf(1));
    assertEquals(
        executableData.extraData,
        Bytes.fromHexString("0xcf8e0d4e9587369b2301d0790347320302cc0943d5a1884560367e8208d920f2"));
    assertEquals(executableData.baseFeePerGas, BigInteger.valueOf(1));
    assertEquals(
        executableData.blockHash,
        Hash.fromHexString("0xcf8e0d4e9587369b2301d0790347320302cc0943d5a1884560367e8208d920f2"));
    assertEquals(executableData.transactions.length, 1);
    assertEquals(
        executableData.transactions[0],
        Transaction.readFrom(
            Bytes.fromHexString(
                "0xf86103018207d094b94f5374fce5edbc8e2a8697c15331677e6ebf0b0a8255441ca098ff921201554726367d2be8c804a7ff89ccf285ebc57dff8ae4c44b9c19ac4aa08887321be575c8095f789dd4c743dfe42c1820f9231f98a962b210e3ac2452a3")));
  }
}
