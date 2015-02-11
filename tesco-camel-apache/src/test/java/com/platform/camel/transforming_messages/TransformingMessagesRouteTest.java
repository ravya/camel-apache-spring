
package com.platform.camel.transforming_messages;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class TransformingMessagesRouteTest extends CamelTestSupport {

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new TransformingMessagesRoute();
    }

    @EndpointInject(uri = "mock:result")
    private MockEndpoint mockResult;

    @Produce(uri = "direct:start")
    protected ProducerTemplate start;

    private static final String XML_INPUT =
            "<root>\n" +
            "    <a>1</a>\n" +
            "    <b>2</b>\n" +
            "    <c>\n" +
            "        <ca>3</ca>\n" +
            "        <cb>4</cb>\n" +
            "    </c>\n" +
            "</root>";

    private static final String EXPECTED_JSON =
            "{\"root\":{" +
                    "\"a\":\"1\"," +
                    "\"b\":\"2\"," +
                    "\"c\":{" +
                        "\"ca\":\"3\"," +
                        "\"cb\":\"4\"}" +
                "}" +
            "}";

    @Test
    public void transformsXmlIntoJson() throws Exception {
        mockResult.expectedBodiesReceived(EXPECTED_JSON);

        start.sendBody(XML_INPUT);
        assertMockEndpointsSatisfied();
    }

}
