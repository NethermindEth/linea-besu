package org.hyperledger.besu.ethereum.blockcreation.builder;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.apache.tuweni.bytes.Bytes;
import org.apache.tuweni.units.bigints.UInt64;
import org.hyperledger.besu.datatypes.Address;
import org.hyperledger.besu.datatypes.Hash;
import org.hyperledger.besu.ethereum.core.Transaction;

import java.math.BigInteger;

public class ExecutableDataDeserializer extends StdDeserializer<ExecutableData> {

    public ExecutableDataDeserializer() {
        this(null);
    }

    public ExecutableDataDeserializer(final Class<?> vc) {
        super(vc);
    }

    @Override
    public ExecutableData deserialize(final JsonParser parser, final DeserializationContext deserializer) throws java.io.IOException {
        ExecutableData executableData = new ExecutableData();
        ObjectCodec codec = parser.getCodec();
        JsonNode node = codec.readTree(parser);

        executableData.parentHash = Hash.fromHexString(node.get("parentHash").asText());
        executableData.feeRecipient = Address.fromHexString(node.get("feeRecipient").asText());
        executableData.stateRoot = Hash.fromHexString(node.get("stateRoot").asText());
        executableData.receiptsRoot = Hash.fromHexString(node.get("receiptsRoot").asText());
        executableData.logsBloom = Bytes.fromHexString(node.get("logsBloom").asText());
        executableData.prevRandao = Hash.fromHexString(node.get("prevRandao").asText());
        executableData.number = UInt64.valueOf(node.get("number").asLong());
        executableData.gasLimit = UInt64.valueOf(node.get("gasLimit").asLong());
        executableData.gasUsed = UInt64.valueOf(node.get("gasUsed").asLong());
        executableData.timestamp = UInt64.valueOf(node.get("timestamp").asLong());
        executableData.extraData = Bytes.fromHexString(node.get("extraData").asText());
        executableData.baseFeePerGas = new BigInteger(node.get("baseFeePerGas").asText());
        executableData.blockHash = Hash.fromHexString(node.get("blockHash").asText());

        Transaction[] transactions = new Transaction[node.get("transactions").size()];
        for (int i = 0; i < node.get("transactions").size(); i++) {
            transactions[i] = Transaction.readFrom(Bytes.fromHexString(node.get("transactions").get(i).asText()));
        }
        executableData.transactions = transactions;
        return executableData;
    }
}
