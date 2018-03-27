<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>wizard向导</title>
    <link href="/static/common/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="/static/library/plugins/wizard/bwizard.min.css" rel="stylesheet" type="text/css"/>
</head>

<body>
<div class="container">
    <h2>Example</h2>
    <div id="wizard">
        <ol>
            <li>Large Paragraph</li>
            <li>Paragraph</li>
            <li>Unordered List</li>
            <li>Kitchen Sink</li>
        </ol>
        <div>
            <p>Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Vestibulum tortor quam, feugiat
                vitae, ultricies eget, tempor sit amet, ante. Donec eu libero sit amet quam egestas semper. Aenean ultricies mi vitae est.
                Mauris placerat eleifend leo. Quisque sit amet est et sapien ullamcorper pharetra. Vestibulum erat wisi, condimentum sed,
                commodo vitae, ornare sit amet, wisi. Aenean fermentum, elit eget tincidunt condimentum, eros ipsum rutrum orci, sagittis
                tempus lacus enim ac dui. Donec non enim in turpis pulvinar facilisis. Ut felis. Praesent dapibus, neque id cursus faucibus,
                tortor neque egestas augue, eu vulputate magna eros eu erat. Aliquam erat volutpat. Nam dui mi, tincidunt quis, accumsan
                porttitor, facilisis luctus, metus</p>
        </div>
        <div>
            <p>Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Vestibulum tortor quam, feugiat
                vitae, ultricies eget, tempor sit amet, ante. Donec eu libero sit amet quam egestas semper. Aenean ultricies mi vitae est.
                Mauris placerat eleifend leo.</p>
        </div>
        <div>
            <ul>
                <li>Lorem ipsum dolor sit amet, consectetuer adipiscing elit.</li>
                <li>Aliquam tincidunt mauris eu risus.</li>
                <li>Vestibulum auctor dapibus neque.</li>
            </ul>
        </div>
        <div>
            <h1>Kitchen Sink</h1>

            <p><strong>Pellentesque habitant morbi tristique</strong> senectus et netus et malesuada fames ac turpis egestas. Vestibulum
                tortor quam, feugiat vitae, ultricies eget, tempor sit amet, ante. Donec eu libero sit amet quam egestas semper. <em>Aenean
                    ultricies mi vitae est.</em> Mauris placerat eleifend leo. Quisque sit amet est et sapien ullamcorper pharetra.
                Vestibulum erat wisi, condimentum sed, <code>commodo vitae</code>, ornare sit amet, wisi. Aenean fermentum, elit eget
                tincidunt condimentum, eros ipsum rutrum orci, sagittis tempus lacus enim ac dui. <a href="#">Donec non enim</a> in turpis
                pulvinar facilisis. Ut felis.</p>

            <h2>Header Level 2</h2>

            <ol>
                <li>Lorem ipsum dolor sit amet, consectetuer adipiscing elit.</li>
                <li>Aliquam tincidunt mauris eu risus.</li>
            </ol>

            <blockquote><p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus magna. Cras in mi at felis aliquet congue. Ut a
                est eget ligula molestie gravida. Curabitur massa. Donec eleifend, libero at sagittis mollis, tellus est malesuada tellus,
                at luctus turpis elit sit amet quam. Vivamus pretium ornare est.</p></blockquote>

            <h3>Header Level 3</h3>

            <ul>
                <li>Lorem ipsum dolor sit amet, consectetuer adipiscing elit.</li>
                <li>Aliquam tincidunt mauris eu risus.</li>
            </ul>

            <pre><code>
			#header h1 a { 
				display: block; 
				width: 300px; 
				height: 80px; 
			}
			</code></pre>
        </div>
    </div>
</div>

<script src="/static/library/jquery/jquery-1.8.3.min.js"></script>
<script src="/static/library/plugins/wizard/bwizard.min.js"></script>
<script type="text/javascript">
    $("#wizard").bwizard({
        activeIndexChanged: function (e, ui) {
            console.log(e);
            console.log(ui.index);
            if (ui.index == 2) {
                $("#wizard").bwizard("show", "0");
            }
        }
    });

</script>
</body>
</html>