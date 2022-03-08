package cn.goroute.smart.search;

import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class SmartSearchApplicationTests {

    @Autowired
    private RestHighLevelClient client;


    @Test
    void contextLoads() {
        System.out.println(client);
    }




}
