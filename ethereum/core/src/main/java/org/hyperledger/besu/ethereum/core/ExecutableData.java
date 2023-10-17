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
package org.hyperledger.besu.ethereum.core;

import org.hyperledger.besu.datatypes.Address;
import org.hyperledger.besu.datatypes.Hash;
import org.hyperledger.besu.ethereum.core.json.ExecutableDataDeserializer;

import java.math.BigInteger;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.collect.Lists;
import org.apache.tuweni.bytes.Bytes;
import org.apache.tuweni.units.bigints.UInt64;

/**
 * Executable data format for building blocks from payload received from external block builders.
 */
@JsonDeserialize(using = ExecutableDataDeserializer.class)
public class ExecutableData {
  public Hash parentHash;
  public Address feeRecipient;
  public Hash stateRoot;
  public Hash receiptsRoot;
  public Bytes logsBloom;
  public Hash prevRandao;
  public UInt64 number;
  public UInt64 gasLimit;
  public UInt64 gasUsed;
  public UInt64 timestamp;
  public Bytes extraData;
  public BigInteger baseFeePerGas;
  public Hash blockHash;
  public Transaction[] transactions;

  public BlockBody toBlockBody() {
    return new BlockBody(Lists.newArrayList(transactions), null);
  }
}
