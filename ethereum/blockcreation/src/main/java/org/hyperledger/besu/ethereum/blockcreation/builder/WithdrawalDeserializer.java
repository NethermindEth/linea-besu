package org.hyperledger.besu.ethereum.blockcreation.builder;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.apache.tuweni.units.bigints.UInt64;
import org.hyperledger.besu.datatypes.Address;

public class WithdrawalDeserializer extends StdDeserializer<Withdrawal> {


    public WithdrawalDeserializer() {
        this(null);
    }

    public WithdrawalDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Withdrawal deserialize(JsonParser parser, DeserializationContext deserializer) throws java.io.IOException{
        Withdrawal withdrawal = new Withdrawal();
        ObjectCodec codec = parser.getCodec();
        JsonNode node = codec.readTree(parser);

        withdrawal.index = UInt64.valueOf(node.get("index").asLong());
        withdrawal.validator = UInt64.valueOf(node.get("validator").asLong());
        withdrawal.address = Address.fromHexString(node.get("address").asText());
        withdrawal.amount = UInt64.valueOf(node.get("amount").asLong());
        return withdrawal;
    }
}
