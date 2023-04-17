# cryptoapp


## Prerequisites
Java >= 17 
Maven

## H2 Console
URL: http://localhost:8080/h2-console <br />
Username: sa <br />
Password: 

## Initial Data
The application is preloaded with some initial data.  <br />
The default user data is loaded from the file `src/main/resources/default-user.json`.
```agsl
{
  "username": "admin",
  "currency": "USDT",
  "amount": 50000
}
```
The currencies data is loaded on startup of the application. <br />
From the file `src/main/resources/pairs-definition.json`
```agsl
BTC
ETH
USDT
```

## API Documentation
The API documentation is available at the following URL: http://localhost:8080/swagger-ui.html

## API Endpoints
### User
- GET /api/v1/users - Get all users
- GET /api/v1/users/{id} - Get user by id

### Currency
