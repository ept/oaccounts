---
title: OAccounts - an open standard for accounting and bookkeeping data portability
layout: home
---

OAccounts
=========

Open standard for companies' financial data - storing and transmitting

Getting started
---------------

* [Introduction to OAccounts](intro.html)


News
----

{% for post in site.posts %}
* [{{ post.title }}](/oaccounts{{ post.url }}) ({{ post.date | date_to_string }})
{% endfor %}

[Subscribe to our feed](http://feeds2.feedburner.com/oaccounts) to keep up-to-date
with OAccounts news!


Copyright
---------

OAccounts is an initiative by [Martin Kleppmann](http://www.yes-no-cancel.co.uk), based on the
[OASIS Universal Business Language (UBL) standard](http://docs.oasis-open.org/ubl/os-UBL-2.0/UBL-2.0.html),
Copyright OASIS Open 2006. The OAccounts standard and all associated material is Copyright by
Martin Kleppmann and respective contributors, and released under the terms of the
[Creative Commons Attribution 3.0 License](http://creativecommons.org/licenses/by/3.0/).
