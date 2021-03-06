# Restaurant App 
Developed a Java project using Eclipse that uses the Springboot application framework. We started developing the application by following a tutorial that was made available through the Springboot website. It asked us in Eclipse to create a new Maven type project with certain dependencies that were provided to us in the tutorial. The purpose of the project was to develop a application to demonstrate how to build a web-based application using a rest API as the back-end and HTML and Javascript as the front-end.

We chose to develop a restaurant ordering system that would allow a customer to place an order for pick-up or delivery.The customer has the option to pick from various menu items, and when they are finished they are prompted for their contact information and finally to submit the order.

We then developed a cook screen where the cook will see any new or cooking orders, these screens have buttons to change the order status. we also built a cashier screen that changes the order status to complete whenever the order has been picked up or delivered to the customer. Menu items may be added or changed through an item management UI also.

The HTML client code interacts with the rest API using jQuery. Items list pending orders and submitting orders all interact with the API. Behind the scenes, the rest API saves data to an embedded SQL database. The table structure for the database is comprised of three tables, food_orders, food_items, food_order_items. 
* Food_items stores the information about the various food items that are available on the menu, including their name, description and price.
* Food_orders contains the information about each order including the persons name, address, telephone number, email and payment type.
* Food_order_items joins the two tables together and represents the items that an order requests.

You will need the following to build/develop further on this project:
* JDK,
* Eclipse or some other IDE,
* Maven (dependencies are managed through Maven and are automatically downloaded for you.)
