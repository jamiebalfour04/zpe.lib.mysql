<h1>zpe.lib.mysql</h1>

<p>
  This is the official MySQL plugin for ZPE.
</p>

<p>
  The plugin provides support for connecting to a MySQL (or MariaDB) database,
  running queries, and executing prepared statements directly from ZPE.
</p>

<h2>Installation</h2>

<p>
  Place <strong>zpe.lib.mysql.jar</strong> in your ZPE native-plugins folder and restart ZPE.
</p>

<p>
  You can also download with the ZULE Package Manager by using:
</p>
<p>
  <code>zpe --zule install zpe.lib.mysql.jar</code>
</p>

<h2>Documentation</h2>

<p>
  Full documentation, examples and API reference are available here:
</p>

<p>
  <a href="https://www.jamiebalfour.scot/projects/zpe/documentation/plugins/zpe.lib.mysql/" target="_blank">
    View the complete documentation
  </a>
</p>

<h2>Example</h2>

<pre>
import "zpe.lib.mysql"

// Connect to the database
db = mysql_connect("localhost", "my_database", "root", "password", 3306)

// Run a simple query
rows = db->query("SELECT id, name FROM users")
print(rows)

// Using a prepared statement
stmt = db->prepare("SELECT id FROM users WHERE id = :id;")

values = [=>]
values[":id"] = 1

result = stmt->execute(values)
print(result)
</pre>

<h2>Notes</h2>

<ul>
  <li>Supports MySQL and compatible servers such as MariaDB.</li>
  <li>The entry point is <code>mysql_connect</code>, which returns a connection object on success.</li>
  <li>Prepared statement placeholders use named parameters (e.g. <code>:id</code>).</li>
  <li>Requires a MySQL JDBC driver (bundled within the plugin).</li>
</ul>
