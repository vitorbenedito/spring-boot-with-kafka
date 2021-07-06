# spring-boot-with-kafka
Tests with spring boot and kafka in an multi-tenancy environment

curl --location --request POST 'http://localhost:9000/products' \
--header 'X-TenantID: TenantOne' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Test product Tenant One - 1",
    "description": "Product 1"
}'

curl --location --request POST 'http://localhost:9000/products' \
--header 'X-TenantID: TenantTwo' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Test product Tenant Two - 2",
    "description": "Product 2"
}'
