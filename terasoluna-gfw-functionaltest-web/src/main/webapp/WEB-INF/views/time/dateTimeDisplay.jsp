
<h2>Server Time</h2>

<span>First Expected Date</span>
<p id="firstExpectedTime">
  <javatime:format value="${firstExpectedTime}" pattern="uuuu-MM-dd HH:mm:ss.SSS" />
</p>
<span>Server Time</span>
<p id="serverTime">
  <javatime:format value="${serverTime}" pattern="uuuu-MM-dd HH:mm:ss.SSS" />
</p>
<span>Last Expected Date</span>
<p id="lastExpectedTime">
  <javatime:format value="${lastExpectedTime}" pattern="uuuu-MM-dd HH:mm:ss.SSS" />
</p>
<span>After Server Time</span>
<p id="afterServerTime">
  <javatime:format value="${afterServerTime}" pattern="uuuu-MM-dd HH:mm:ss.SSS" />
</p>
<p id="serverTimeZone">
  ${serverTimeZone}
</p>
