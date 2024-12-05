# fetch-rewards-challenge
**Steps to build:**
1. Navigate to the root directory of project where Dockerfile is located.
2. Execute below docker command for build: </br>
	docker build -t receipt-processor .

**Steps to run:** </br>
docker run -p 80:8080 receipt-processor

**Application is accessible at:** </br>
http://localhost/swagger-ui/index.html

**Endpoints:** </br>
POST: http://localhost:8080/receipts/process</br>
GET: http://localhost:8080/receipts/{id}/points</br>
