# Online Bookstore API

Welcome to the Online Bookstore API! This API powers an online bookstore application where users can view books, their authors, and genres, make orders, cancel orders, complete them, and much more.

📚 [Explore the Swagger API Documentation](https://online-bookstore-izaiah.onrender.com/swagger-ui/index.html)

## Functionality

- View Books: Retrieve information about available books including title, author, genre, and price.
- View Authors: Get details about authors including their names and associated books.
- View Genres: Explore different book genres and the books available in each genre.
- Make Orders: Place orders for books, specifying the quantity desired.
- Cancel Orders: Cancel existing orders before they are completed.
- Complete Orders: Mark orders as completed once they are fulfilled.

## Roles

The application supports three main roles:

- **User**: Standard users who can browse books, make orders, etc.
- **Manager**: Users with managerial privileges.
- **Admin**: Users with administrative privileges.

## Default Accounts

Upon starting the application, the following default accounts are created:

### Manager

- **Email:** manager@mail.com
- **Password:** #Admin123

### Admin

- **Email:** admin@mail.com
- **Password:** #Admin123

## Endpoints

Here are some of the main endpoints available in the API:

- **GET /books**: Retrieve all books.
- **GET /authors**: Retrieve all authors.
- **GET /genres**: Retrieve all genres.
- **POST /orders**: Place a new order.
- **PUT /orders/{orderId}/cancel**: Cancel a specific order.
- **PUT /orders/{orderId}/complete**: Mark a specific order as completed.

For more details and additional endpoints, please refer to the [API Documentation](https://online-bookstore-izaiah.onrender.com/swagger-ui/index.html).
