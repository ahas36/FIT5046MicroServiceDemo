# FIT5046MicroServiceSample
A basic demonstration of deploying mircroserivce on Docker

# To build:
Open all the three application in Netbeans IDE
1- Build the PizzaEntity (right click on the project and select Clean and Build)

2- Repeast the step 1 for PizzaOrder and CookPizza

3- Go to CookPizza folder and build the image using this command:

docker build --no-cache -t fit5046/cook-pizza .

3- Repeat the step above for PizzaOrder

docker build --no-cache -t fit5046/pizza-order .

4- Go to the root folder and perform this command:

docker-compose up
