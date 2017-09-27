<!DOCTYPE html>
<html>
  <head>
    <title>Details on {{ unicorn.name }}</title>
    <link rel='stylesheet' href='/static/style.css' />
    <link href='http://fonts.googleapis.com/css?family=Great+Vibes' rel='stylesheet' type='text/css'>
    <meta http-equiv="content-type" content="text/html;charset=utf-8" />
  </head>
  <body>
    <h1>{{ unicorn.name }}</h1>
    <article>
      <section id="description">
        <blockquote>
          <p>{{ unicorn.description }}</p>
        </blockquote>
        <div id="metadata">
          <div id="reportedBy">{{ unicorn.reportedBy }}, </div>
          <div id="reportedWhen">{{ unicorn.spottedWhen }}</div>
        </div>
      </section>
      <section id="image">
        <a href="{{ unicorn.image }}"><img alt="unicorn #{{ unicorn.id }}" src="{{ unicorn.image }}"></a>
      </section>
      <section id="location">
        Sågs i {{ unicorn.spottedWhere.name }} (lat {{ unicorn.spottedWhere.lat }}, lon {{ unicorn.spottedWhere.lon }})
      </section>
    </article>
  </body>
</html>
