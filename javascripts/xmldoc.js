function unfoldChildren(query) {
	query.click(function(evt) {
		evt.preventDefault();
		var frag = this.href.replace(/(.*)(\.html)$/, '$1_frag$2');
		$(this).parent().load(frag, function() {
			unfoldChildren($(this).find('a.open'));
			unfoldAttributes($(this).find('div.tag'));
		});
		return false;
	});
}

function unfoldAttributes(query) {
	query.each(function() {
		var attrs = $(this).find('span.att.optional');
		if (attrs.length > 0) {
			$(attrs.get(attrs.length - 1)).after('<a href="#" class="showatt">...</a>');
			$(this).find('a.showatt').click(function(evt) {
				evt.preventDefault();
				$(this).parent().find('span.att.optional').css('display', 'inline');
				$(this).css('display', 'none');
				return false;
			});
		}
	});
}

$(document).ready(function() {
	unfoldChildren($('div.el a.open'));
	unfoldAttributes($('div.tag'));
});
