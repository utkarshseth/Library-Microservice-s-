# Library-Microservice(s)
{USE RAW OPTION ON RIGHT TO VIEW THIS FOR BETTER INDENTATION}

Backend project for Library related microservices

Library Project: Backend
========================

Backend tools used - Maven,Spring Boot,Spring-Cloud-Netflix,Spring-Cloud-Gateway, 
		      Spring Cloud Circuit Breaker,Spring Cloud Sleuth,
		      Zipkin , Mysql

Have: Multiple services running on different port to simulate Microservices Structure.
 

Book service - Add book to store, Get all books
Order service - Place order for books , Interact with Inventory service via rest call to check if book in stock.
Inventory service - Maintains inventory which order service checks for a successful order 

{ABOVE SERVICES ACT AS CLIENT}
Discovery service - Act as a DISCOVERY_SERVER which client reaches out to get registry containing ip and port for a service .
Gateway service   - Act as a API_GATEWAY to bifurcate request to different microservice .

(( Flow ))

User(can call on 8080 port) ->  Gateway Service(bifurcates call to) : (Either) Product Service
             				        	              :   (Or)   Order Service----------->   (calls) Inventory service{to see if order in stock)
	
Synchronus Communication   :: between Order and Inventory service is happening through Rest call.

Circuit Breaker            :: Using Resilience4j for circuit breaker feature.Implemented this in order service as it calls
				another microservice- Inventory service so to put timeouts and retry mechanism between them its used. 

Load Balancing Enabled     :: Web client used to interact between service is load balanced enabled to handle multiple instance running case .

Service Discovery pattern  :: Using Spring-Cloud-Netflix to register Discovery service as a SERVER and other services as its CLIENT.
			       When Order Service wants to call Inventory service , it uses Webclient Builder With LOAD BALANCING enabled
			       i.e. asks DISCOVERY SERVER to give ip and port of inventory service as multiple instances of inventory
			       service can be running and Client(order service) stores this registry(Data having Ip,Port of Other Service)
			       with itself.
				
	 		       If next time for some reason if Discovery Service is down , client(Order service) can use previously stored registry info
			       to fetch ip,port of other services.
			     
Api Gateway Pattern        :: Using Spring-Cloud-Gateway to give a single address to user to call since all service running on different port
			       Gateway service acts as a LOAD BALANCER to bifurcate requests to different downstream service.
			       Uses defined Rules to call corresponding service.
Distributed Tracing        :: Using Spring Cloud Sleuth for implementing Distributed Tracing And Zipkin tool to visualize this .
			       It adds traceId in logs which is same for a request across all the microservices involved .
			       ZIpking helps visualize that request went from which microservice to another and how much time each service took.


