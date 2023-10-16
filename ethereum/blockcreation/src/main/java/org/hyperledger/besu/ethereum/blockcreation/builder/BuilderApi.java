package org.hyperledger.besu.ethereum.blockcreation.builder;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.hyperledger.besu.datatypes.BLSPublicKey;
import org.hyperledger.besu.datatypes.Hash;
import org.hyperledger.besu.ethereum.core.BlockBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


/**
 * Builder API service
 */
public class BuilderApi {

    private static final Logger LOG = LoggerFactory.getLogger(BuilderApi.class);

    OkHttpClient client;
    String endpoint;

    BLSPublicKey publicKey;

    public BuilderApi(final String endpoint, final BLSPublicKey blsPublicKey) {
        this.endpoint = endpoint;
        this.client = new OkHttpClient();
        this.publicKey = blsPublicKey;
    }

    public BlockBody fetchBlockBody(final long slot, final Hash parentHash) throws IOException {
        Request request = new Request.Builder()
                .addHeader("Accept", "application/json; q=0.5")
                .url(String.format("%s/eth/v1/builder/block/%d/%s/%s", this.endpoint,
                        slot, parentHash.toHexString(), this.publicKey.toHexString())).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Block Request failed: " + response);
            if (response.body() == null) throw new IOException("Block body is empty");
            ObjectMapper objectMapper = new ObjectMapper();
            ExecutionPayloadResponse payload = objectMapper.readValue(response.body().string(), ExecutionPayloadResponse.class);
            LOG.info("Got Block from mev-boost-relay: {}", payload.data.blockHash.toHexString());
            return payload.data.toBlockBody();
        }
    }
}
