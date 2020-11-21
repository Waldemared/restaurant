package com.wald.restaurant.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import javax.sql.DataSource


@Configuration
open class TestConfig {

    @Bean
    open fun dataSource(): DataSource {
        return EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("$SCRIPTS_DIRECTORY_LOCATION/psql_schema.sql")
                .addScript("$SCRIPTS_DIRECTORY_LOCATION/data.sql")
                .addScript("$TEST_SCRIPTS_DIRECTORY_LOCATION/data.sql")
                .build()
    }

    companion object {
        const val SCRIPTS_DIRECTORY_LOCATION = "classpath:/db"
        const val TEST_SCRIPTS_DIRECTORY_LOCATION = "classpath:/testConfig/db"
    }
}