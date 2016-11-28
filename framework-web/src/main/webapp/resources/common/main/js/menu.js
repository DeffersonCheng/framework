/**
 * Created by Sean on 4/9/16.
 */

(function($) {
	'use strict';
	var MenuToolBar = function($el, options) {
		this.$el = $($el);
		this.options = options;

		this.init();
	};

	MenuToolBar.DEFAULT = {
		url : ''
	};

	MenuToolBar.prototype.initHtml = function() {
		var _this = this;
		_this.$container = $('<nav class="menu-container"></nav>');
		_this.$TopLevelMenu = $('<ul></ul>');

		var openedItem = '';

		ajaxPost(MenuToolBar.DEFAULT.url, null, function(data) {
			if (!data)
				return;
			$.each(data,
					function(index, item) {
						// top menu
						var $li = $('<li><a href="#">' + '<div><i class="fa '
								+ item.icon + ' fa-2x"></i></div>' + '<div>'
								+ item.text + '</div></a>'
								+ '<div class="arrow-left"></div>' + '</li>');

						// open item or link
						$li.on('click', function() {
							$li.siblings().removeClass('item-click');
							$li.removeClass('item-click')
									.addClass('item-click');
							$li.siblings().find('ul').hide();
							if (item.url != null && item.url != '') {
								loadPage(basePath + item.url);
								$li.siblings().find('.arrow-left').hide();
								$li.find('.arrow-left').show();
								// 记录上次打开的item
								openedItem = $li;
							} else {
								// open item
								var _$ul = $li.find('ul');
								_$ul.show();
								$li.find('.arrow-left').hide();
								var winHeight = $(window).height();
								var ulTop = _$ul.offset().top;
								var ulHeight = _$ul.height();
								if (ulTop + ulHeight > winHeight) {
									var topDistance = ulTop
											- (winHeight - ulHeight) + 15;
									_$ul.css('-moz-transform', 'translateY(-'
											+ topDistance + 'px)');
									_$ul.css('-ms-transform', 'translateY(-'
											+ topDistance + 'px)');
									_$ul.css('-webkit-transform',
											'translateY(-' + topDistance
													+ 'px)');
									_$ul.css('transform', 'translateY(-'
											+ topDistance + 'px)');
								}
							}
							return false;
						});
						var $childrenUl = $('<ul></ul>');
						$.each(item.children, function(idx, child) {
							var $childrenLi = $('<li><a href="#"><i class="fa '
									+ child.icon + '"></i>' + child.text
									+ '</a></li>');
							$childrenLi.on('click', function() {
								loadPage(basePath + child.url);
								setTimeout(function() {
									$childrenUl.hide();
									$li.siblings().find('.arrow-left').hide();
									$li.find('.arrow-left').show();
								}, 100);
								// 记录上次打开的item
								openedItem = $li;
								return false;
							});
							$childrenUl.append($childrenLi);
						});

						$(document)
								.on(
										'click',
										function() {
											if (openedItem != null
													&& openedItem != '') {
												openedItem.siblings()
														.removeClass(
																'item-click');
												openedItem.removeClass(
														'item-click').addClass(
														'item-click');
												openedItem.find('.arrow-left')
														.show();
											} else {
												$li.removeClass('item-click');
											}

											$childrenUl.hide();
										});

						$li.append($childrenUl);
						_this.$TopLevelMenu.append($li);
					});
			_this.$container.append(_this.$TopLevelMenu);

			_this.$el.append(_this.$container);
		});

	};

	MenuToolBar.prototype.event = function() {

	};

	// MenuToolBar.prototype.

	MenuToolBar.prototype.loadData = function(callback) {
		var _this = this;

	};

	MenuToolBar.prototype.init = function() {
		this.initHtml();
	};

	$.fn.MenuToolBar = function(options) {
		options = $.extend(MenuToolBar.DEFAULT, options);
		return new MenuToolBar(this, options);
	};
})(jQuery);