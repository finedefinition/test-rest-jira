# Dynamic REST
Dynamic REST. Rest accept a JQL string, and return set of fields. REST perform a JQL search and return JSON with the selected fields. The result (issues) output without limitation on the number of displays.

#### Before you begin.<br>
Install the [Atlassian Plugin SDK](https://developer.atlassian.com/server/framework/atlassian-sdk/), you'll also need the following: <br>
Java SE Development Kit (JDK) 8 or AdoptOpenJDK 8 <br>
Your JAVA_HOME variable set <br>

#### Before first run <br>
JIRA plugin use maven installing inside pligin package so you need to change path to your Maven directory in your IDE <br>

Path to Maven in MacOS
usr/local/Cellar/atlassian-plugin-sdk/8.2.7/libexec/apache-maven-3.5.4 <br>

`atlas-run`

#### Searching for issues using POST <br>
If your JQL query is too large to specify in a URL parameter, you can POST your JQL query (in JSON format) <br>
to the Jira REST API search resource instead. Any additional URL parameters (apart from the url parameter) <br>
described above must be included in your JSON-formatted JQL query.<br>
<br>
#### Endpoint 
http://localhost:2990/jira/rest/restresource/1.0/search <br>
<br>
#### Request <br>
{<br>
"projectKey": "TEST",<br>
"fields": ["description"],<br>
"properties": ["project", "issuetype", "assignee"],<br>
"fieldsByKeys": false,<br>
"jql": "project = 'TEST' AND issuetype = 'Task' AND assignee = 'admin'",<br>
"maxResults": 1,<br>
"startAt": 0,<br>
"validateQuery": "strict",<br>
##### "fetchAll": false //this switcher to output without limitation on the number of displays.<br>
}<br>
<br>
#### Response<br>
{<br>
"maxResults": 1,<br>
"startAt": 0,<br>
"total": 6,<br>
"expand": "schema,names",<br>
"issues": [<br>
{<br>
"key": "TEST-6",<br>
"description": "h4. Now it's your turn!\n{color:#707070}Now, it is your turn to create your first task. Click the <br>
'Create' button and get your project started. {color}\nh4. If you are a JIRA Core administrator, you can create <br>
....}<br>

#### Response if switcher fetchAll turn on:
![](images/response.png)

Full documentation is always available at:

https://developer.atlassian.com/display/DOCS/Introduction+to+the+Atlassian+Plugin+SDK


