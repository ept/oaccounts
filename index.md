---
title: OAccounts - an open standard for accounting and bookkeeping data portability
layout: home
---

OAccounts -- Open Accounting Data
=================================

What is OAccounts?
------------------

OAccounts is an initiative to create an **open standard** for representing organisations'
**detailed financial data** -- the kind of data which is typically held by accounting,
bookkeeping or invoicing software packages. Because it is an open standard, anybody may
use it in their applications without any fees, permission or licensing required.

The aims of OAccounts are:

* To define a free and open standard for storing and transmitting accounting data,
  particularly suitable for small and medium-sized businesses.
* To enable interoperability and data synchronisation between different accounting
  software systems.
* To support multiple currencies, taxation in different jurisdictions, and other features
  which are important for an internationally growing business, independent of the
  country in which it is based.


Why OAccounts?
--------------

Accounting software is currently a market dominated by a small number of large, established
players with competition from numerous start-ups. Most software providers, both large and
small, are storing their data in proprietary formats; this makes it harder for customers
to move from using one application to another, and this **"vendor lock-in"** is perceived
as a competitive advantage by many software providers.

We argue that a common open standard, supported by many different applications, would be
an advantage to everybody. Reasons include:

* Each user who works with an organisation's accounting data can use the tool of their
  preference (the software package for which they have been trained, or which offers the
  best features to support a given task), and it becomes possible to use several different
  applications in parallel without creating conflicts between the data sets.
* It becomes much easier for third-party developers to create tools which extend existing
  accounting packages and add value to them; for example, integration with online shopping
  cart and subscription billing systems, credit card payment providers, tax and accounting
  advisors, or custom reporting tools. All software vendors whose application is integrated
  with the OAccounts standard can benefit from an open ecosystem of third-party applications
  which are built around OAccounts and thus immediately interoperable with their application.
* Less is lost in the case of an accounting software provider going out of business,
  because users can immediately continue working with the data through a different software
  package. (In an uncertain economic climate, both large and small software vendors are at
  a risk of going out of business.)
* Organisations can combine the benefits of hosted "Software-as-a-Service" accounting
  software (ease of access, lower maintenance cost, frequent updates and support) with
  a synchronised copy of the data on their own systems (for custom processing tasks requiring
  fast local data access, and for backup, audit and disaster recovery purposes).


What is the current status of OAccounts?
----------------------------------------

OAccounts is currently an **early draft**, and we are currently seeking contributions
from anybody with an interest in interchange and processing of accounting data. If you
would like to contribute, please contact us (see below).

OAccounts builds on the
[OASIS Universal Business Language (UBL) standard](http://docs.oasis-open.org/ubl/os-UBL-2.0/UBL-2.0.html),
which has been mature for years and is being adopted in some jurisdictions as mandatory
electronic data interchange format for public-sector billing. UBL focuses on providing the
tools and protocols for negotiating and executing one particular transaction. OAccounts
aggregates all of an organisation's transactions (represented in UBL format) into a
repository representing that organisation's accounts.


News
----

{% for post in site.posts %}
* [{{ post.title }}](/oaccounts{{ post.url }}) ({{ post.date | date_to_string }})
{% endfor %}

Blog posts discussing OAccounts:
* [Martin Kleppmann, Yes/No/Cancel](http://www.yes-no-cancel.co.uk/2009/03/04/oaccounts-setting-your-accounts-data-free/)
* [Dennis Howlett, AccMan](http://www.accmanpro.com/2009/03/16/oaccounts-is-it-feasible/)
* [Ben Kepes, CloudAve](http://www.cloudave.com/link/finding-a-babel-fish-for-data)

[Subscribe to our feed](http://feeds2.feedburner.com/oaccounts) to keep up-to-date
with OAccounts news!


Copyright
---------

OAccounts is an initiative by [Martin Kleppmann](http://www.yes-no-cancel.co.uk), based on the
[OASIS Universal Business Language (UBL) standard](http://docs.oasis-open.org/ubl/os-UBL-2.0/UBL-2.0.html),
Copyright OASIS Open 2006. The OAccounts standard and all associated material is Copyright by
Martin Kleppmann and respective contributors, and released under the terms of the
[Creative Commons Attribution 3.0 License](http://creativecommons.org/licenses/by/3.0/).

If you have any questions or would like to contribute, please contact
Martin Kleppmann (martin at eptcomputing dot com) who is currently coordinating the
OAccounts efforts.
