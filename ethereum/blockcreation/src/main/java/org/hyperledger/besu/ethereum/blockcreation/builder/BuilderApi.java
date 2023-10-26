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
package org.hyperledger.besu.ethereum.blockcreation.builder;

import org.hyperledger.besu.datatypes.BLSPublicKey;
import org.hyperledger.besu.datatypes.Hash;
import org.hyperledger.besu.ethereum.core.BlockBody;
import org.hyperledger.besu.ethereum.core.ExecutionPayloadResponse;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Builder API service */
public class BuilderApi {

  private static final Logger LOG = LoggerFactory.getLogger(BuilderApi.class);

  OkHttpClient client;
  public String endpoint;

  public BLSPublicKey publicKey;

  public BuilderApi(final String endpoint, final BLSPublicKey blsPublicKey) {
    this.endpoint = endpoint;
    this.client =
        new OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.SECONDS)
            .writeTimeout(1, TimeUnit.SECONDS)
            .build();
    this.publicKey = blsPublicKey;
  }

  public BlockBody fetchBlockBody(final long slot, final Hash parentHash) throws IOException {
    Request request =
        new Request.Builder()
            .addHeader("Accept", "application/json; q=0.5")
            .url(
                String.format(
                    "%s/eth/v1/builder/block/%d/%s/%s",
                    this.endpoint, slot, parentHash.toHexString(), this.publicKey.toHexString()))
            .build();

    try (Response response = client.newCall(request).execute()) {
      if (!response.isSuccessful()) throw new IOException("Block Request failed: " + response);
      if (response.body() == null) throw new IOException("Block body is empty");
      ObjectMapper objectMapper = new ObjectMapper();
      ExecutionPayloadResponse payload =
          objectMapper.readValue(response.body().string(), ExecutionPayloadResponse.class);
      LOG.info("Got Block from mev-boost-relay: {}", payload.bellatrix.blockHash.toHexString());
      return payload.bellatrix.toBlockBody();
    }
  }
}
