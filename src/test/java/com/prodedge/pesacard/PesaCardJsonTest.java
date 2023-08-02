package com.prodedge.pesacard;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.assertj.core.util.Arrays;
import com.prodedge.pesacard.model.PesaCard;
import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class PesaCardJsonTest {
    @Autowired
    private JacksonTester<PesaCard> json;

    @Autowired
    private JacksonTester<PesaCard[]> jsonList;

    private PesaCard[] pesaCards;

    @BeforeEach
    void setUp() {
        pesaCards = Arrays.array(
                new PesaCard(99L, 123.45),
                new PesaCard(100L, 1.0),
                new PesaCard(101L, 150.0));
    }

    // @Test
    // public void cashCardSerializationTest() throws IOException {
    // PesaCard cashCard = new PesaCard(99L, 123.45);
    // assertThat(json.write(cashCard)).isStrictlyEqualToJson("expected.json");
    // assertThat(json.write(cashCard)).hasJsonPathNumberValue("@.id");
    // assertThat(json.write(cashCard)).extractingJsonPathNumberValue("@.id").isEqualTo(99);
    // assertThat(json.write(cashCard)).hasJsonPathNumberValue("@.amount");
    // assertThat(json.write(cashCard)).extractingJsonPathNumberValue("@.amount").isEqualTo(123.45);
    // }

    @Test
    public void pesaCardSerializationTest() throws IOException {
        PesaCard cashCard = pesaCards[0];
        assertThat(json.write(cashCard)).isStrictlyEqualToJson("single.json");
        assertThat(json.write(cashCard)).hasJsonPathNumberValue("@.id");
        assertThat(json.write(cashCard)).extractingJsonPathNumberValue("@.id")
                .isEqualTo(99);
        assertThat(json.write(cashCard)).hasJsonPathNumberValue("@.amount");
        assertThat(json.write(cashCard)).extractingJsonPathNumberValue("@.amount")
                .isEqualTo(123.45);
    }

    @Test
    public void cashCardDeserializationTest() throws IOException {
        String expected = """
                {
                    "id":1000,
                    "amount":67.89
                }
                """;
        assertThat(json.parse(expected))
                .isEqualTo(new PesaCard(1000L, 67.89));
        assertThat(json.parseObject(expected).id()).isEqualTo(1000);
        assertThat(json.parseObject(expected).amount()).isEqualTo(67.89);
    }

    @Test
    void cashCardListSerializationTest() throws IOException {
        assertThat(jsonList.write(pesaCards)).isStrictlyEqualToJson("list.json");
    }

    @Test
    void cashCardListDeserializationTest() throws IOException {
        String expected = """
                [
                   { "id": 99, "amount": 123.45 },
                   { "id": 100, "amount": 1.0 },
                   { "id": 101, "amount": 150.00 }
                ]
                """;
        assertThat(jsonList.parse(expected)).isEqualTo(pesaCards);
    }
}
