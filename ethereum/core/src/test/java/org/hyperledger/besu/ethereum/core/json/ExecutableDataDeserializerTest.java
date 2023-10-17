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
                         "parentHash": "0xcf8e0d4e9587369b2301d0790347320302cc0943d5a1884560367e8208d920f2",
                         "feeRecipient": "0xabcf8e0d4e9587369b2301d0790347320302cc09",
                         "stateRoot": "0xcf8e0d4e9587369b2301d0790347320302cc0943d5a1884560367e8208d920f2",
                         "receiptsRoot": "0xcf8e0d4e9587369b2301d0790347320302cc0943d5a1884560367e8208d920f2",
                         "logsBloom": "0x00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
                         "prevRandao": "0xcf8e0d4e9587369b2301d0790347320302cc0943d5a1884560367e8208d920f2",
                         "number": "1",
                         "gasLimit": "1",
                         "gasUsed": "1",
                         "timestamp": "1",
                         "extraData": "0xcf8e0d4e9587369b2301d0790347320302cc0943d5a1884560367e8208d920f2",
                         "baseFeePerGas": "1",
                         "blockHash": "0xcf8e0d4e9587369b2301d0790347320302cc0943d5a1884560367e8208d920f2",
                         "transactions": [
                            "0x02f878831469668303f51d843b9ac9f9843b9aca0082520894c93269b73096998db66be0441e836d873535cb9c8894a19041886f000080c001a031cc29234036afbf9a1fb9476b463367cb1f957ac0b919b69bbc798436e604aaa018c4e9c3914eb27aadd0b91e10b18655739fcf8c1fc398763a9f1beecb8ddc86"
                         ],
                         "withdrawals": [
                           {
                             "index": "1",
                             "validator_index": "1",
                             "address": "0xabcf8e0d4e9587369b2301d0790347320302cc09",
                             "amount": "32000000000"
                           }
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
        Bytes.fromHexString(
            "0x02f878831469668303f51d843b9ac9f9843b9aca0082520894c93269b73096998db66be0441e836d873535cb9c8894a19041886f000080c001a031cc29234036afbf9a1fb9476b463367cb1f957ac0b919b69bbc798436e604aaa018c4e9c3914eb27aadd0b91e10b18655739fcf8c1fc398763a9f1beecb8ddc86"));
  }
}
