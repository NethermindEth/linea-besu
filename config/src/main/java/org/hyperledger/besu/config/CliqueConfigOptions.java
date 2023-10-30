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
package org.hyperledger.besu.config;

import org.hyperledger.besu.datatypes.BLSPublicKey;

import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** The Clique config options. */
public class CliqueConfigOptions {

  /** The constant DEFAULT. */
  public static final CliqueConfigOptions DEFAULT =
      new CliqueConfigOptions(JsonUtil.createEmptyObjectNode());

  private static final long DEFAULT_EPOCH_LENGTH = 30_000;
  private static final int DEFAULT_BLOCK_PERIOD_SECONDS = 15;

  private final ObjectNode cliqueConfigRoot;

  private static final Logger LOG = LoggerFactory.getLogger(CliqueConfigOptions.class);

  /**
   * Instantiates a new Clique config options.
   *
   * @param cliqueConfigRoot the clique config root
   */
  CliqueConfigOptions(final ObjectNode cliqueConfigRoot) {
    this.cliqueConfigRoot = cliqueConfigRoot;
  }

  /**
   * The number of blocks in an epoch.
   *
   * @return the epoch length
   */
  public long getEpochLength() {
    return JsonUtil.getLong(cliqueConfigRoot, "epochlength", DEFAULT_EPOCH_LENGTH);
  }

  /**
   * Gets block period seconds.
   *
   * @return the block period seconds
   */
  public int getBlockPeriodSeconds() {
    return JsonUtil.getPositiveInt(
        cliqueConfigRoot, "blockperiodseconds", DEFAULT_BLOCK_PERIOD_SECONDS);
  }

  public Optional<String> getBuilderApiEndpoint() {
    String endpoint = JsonUtil.getString(cliqueConfigRoot, "builderapiendpoint", "");
    LOG.info("endpoint: {}", endpoint);
    if (endpoint.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(endpoint);
  }

  public Optional<BLSPublicKey> getProposerPubKey() {
    String pubKeyHex = JsonUtil.getString(cliqueConfigRoot, "proposerpubkey", "");
    LOG.info("pubkey: {}", pubKeyHex);
    if (pubKeyHex.isEmpty()) {
      return Optional.empty();
    }

    BLSPublicKey blsPublicKey = BLSPublicKey.fromHexString(pubKeyHex);
    return Optional.of(blsPublicKey);
  }

  /**
   * As map.
   *
   * @return the map
   */
  Map<String, Object> asMap() {
    return ImmutableMap.of(
        "epochLength",
        getEpochLength(),
        "blockPeriodSeconds",
        getBlockPeriodSeconds(),
        "builderApiEndpoint",
        getBuilderApiEndpoint(),
        "proposerPublicKey",
        getProposerPubKey());
  }
}
