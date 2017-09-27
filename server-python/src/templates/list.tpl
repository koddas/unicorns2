<!DOCTYPE html>
<html>
  <head>
    <title>List of unicorns</title>
    <link rel='stylesheet' href='/static/style.css' />
    <link href='http://fonts.googleapis.com/css?family=Great+Vibes' rel='stylesheet' type='text/css'>
    <meta http-equiv="content-type" content="text/html;charset=utf-8" />
  </head>
  <body>
    <h1>The fantastic unicorn database</h1>
    <ul>
  {% for unicorn in unicorns %}
      <li class="unicorn" id="unicorn{{ unicorn.id }}">
        <a href="unicorns/{{ unicorn.id }}">{{ unicorn.name }}</a>
      </li>
  {% endfor %}
     </ul>
  </body>
</html>
