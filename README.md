# Data Bases 2
### Web Application project of the year 2022
## PROJECT
The Telco web application is an application that simulates the use and functionality of a telephone company website, with associated database.

Users have the option of purchasing packages, which may include one or more services and zero or more optional products. There are 4 types of services: fixed telephone, mobile telephone, fixed internet, and mobile internet. Each package is associated with one or more validity periods and a corresponding monthly cost. Once the user selects the package and its validity, that validity will be applied to all services included in that package and the optional products selected. The monthly cost will depend on the selected validity, plus the costs of the optional products.

A user can configure a package even without being logged in, but the order will only be granted to registered and logged in users.

If the package payment fails, the user will be categorized as insolvent and will be shown on his/her home page the option to retry the payment. In case of 3 consecutive failed payments by the same user (either on the same package or on different packages), an alert will be created with his/her data. Once the number of failed payments is less than 3 the alert will be removed. When all payments have been done, the user will no longer be insolvent.

An employee, once logged into the platform, has the ability to create optional products and configure packages that can be purchased by users. He/She also has the ability to view some reports on user behavior and sales performance. The reports are the following:
<ul>
  <li> Number of total purchases per package. </li>
  <li> Number of total purchases per package and validity period. </li>
  <li> Total value of sales per package with and without the optional products. </li>
  <li> Average number of optional products sold together with each service package. </li>
  <li> List of insolvent users, suspended orders and alerts. </li>
  <li> Best seller optional product, i.e. the optional product with the greatest value of sales across all the sold service packages. </li>
</ul>
