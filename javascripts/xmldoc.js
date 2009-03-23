function hijackLinks(query) {
	query.click(function(evt) {
		evt.preventDefault();
		var frag = this.href.replace(/(.*)(\.html)$/, '$1_frag$2');
		$(this).parent().load(frag, function() {
			hijackLinks($(this).find('a'));
		});
		return false;
	});
}

$(document).ready(function() {
	hijackLinks($('div.el a'));
});
