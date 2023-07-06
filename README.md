# order-manager rest api

HOW TO RUN: </br>
database name: order-manager </br>
creation of tables = done by flyway on runtime </br>


HOW TO CALL THE ROUTES: </br>


 *things to do / observations*: </br>
 -more validations to assure the consistency and integrity of data. </br>
 -activate and deactivate entities insted of deleting from database. </br>
 -authentication (jwt). </br>
 -run on docker. </br>
 -events with message broker(rabbitmq or kafka), especially in the order and stock movement operations, would be important to the resilience. </br>
 -unit and integration test (junit, mockito, wiremock). </br>
 -grafana to help visualize/monitor the metrics </br>
 -kibana to help with the log messages </br>
 
