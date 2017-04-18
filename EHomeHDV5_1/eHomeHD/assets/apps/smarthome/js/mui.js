/*!
 * =====================================================
 * Mui v1.6.0 (https://github.com/dcloudio/mui)
 * =====================================================
 */
var mui = (function(h, d) {
	var b = /complete|loaded|interactive/;
	var f = /^#([\w-]+)$/;
	var g = /^\.([\w-]+)$/;
	var j = /^[\w-]+$/;
	var a = /translate(?:3d)?\((.+?)\)/;
	var k = /matrix(3d)?\((.+?)\)/;
	var e = function(l, m) {
		m = m || h;
		if(!l) {
			return c()
		}
		if(typeof l === "object") {
			return c([l], null)
		}
		if(typeof l === "function") {
			return e.ready(l)
		}
		if(typeof l === "string") {
			try {
				l = l.trim();
				if(f.test(l)) {
					var n = h.getElementById(RegExp.$1);
					return c(n ? [n] : [])
				}
				return c(e.qsa(l, m), l)
			} catch(o) {}
		}
		return c()
	};
	var c = function(m, l) {
		m = m || [];
		Object.setPrototypeOf(m, e.fn);
		m.selector = l || "";
		return m
	};
	e.uuid = 0;
	e.data = {};
	e.extend = function() {
		var u, n, l, m, r, s, q = arguments[0] || {},
			p = 1,
			o = arguments.length,
			t = false;
		if(typeof q === "boolean") {
			t = q;
			q = arguments[p] || {};
			p++
		}
		if(typeof q !== "object" && !e.isFunction(q)) {
			q = {}
		}
		if(p === o) {
			q = this;
			p--
		}
		for(; p < o; p++) {
			if((u = arguments[p]) != null) {
				for(n in u) {
					l = q[n];
					m = u[n];
					if(q === m) {
						continue
					}
					if(t && m && (e.isPlainObject(m) || (r = e.isArray(m)))) {
						if(r) {
							r = false;
							s = l && e.isArray(l) ? l : []
						} else {
							s = l && e.isPlainObject(l) ? l : {}
						}
						q[n] = e.extend(t, s, m)
					} else {
						if(m !== d) {
							q[n] = m
						}
					}
				}
			}
		}
		return q
	};
	e.noop = function() {};
	e.slice = [].slice;
	e.filter = [].filter;
	e.type = function(l) {
		return l == null ? String(l) : i[{}.toString.call(l)] || "object"
	};
	e.isArray = Array.isArray || function(l) {
		return l instanceof Array
	};
	e.isWindow = function(l) {
		return l != null && l === l.window
	};
	e.isObject = function(l) {
		return e.type(l) === "object"
	};
	e.isPlainObject = function(l) {
		return e.isObject(l) && !e.isWindow(l) && Object.getPrototypeOf(l) === Object.prototype
	};
	e.isEmptyObject = function(m) {
		for(var l in m) {
			if(l !== d) {
				return false
			}
		}
		return true
	};
	e.isFunction = function(l) {
		return e.type(l) === "function"
	};
	e.qsa = function(l, m) {
		m = m || h;
		return e.slice.call(g.test(l) ? m.getElementsByClassName(RegExp.$1) : j.test(l) ? m.getElementsByTagName(l) : m.querySelectorAll(l))
	};
	e.ready = function(l) {
		if(b.test(h.readyState)) {
			l(e)
		} else {
			h.addEventListener("DOMContentLoaded", function() {
				l(e)
			}, false)
		}
		return this
	};
	e.map = function(p, q) {
		var o, l = [],
			n, m;
		if(typeof p.length === "number") {
			for(n = 0, len = p.length; n < len; n++) {
				o = q(p[n], n);
				if(o != null) {
					l.push(o)
				}
			}
		} else {
			for(m in p) {
				o = q(p[m], m);
				if(o != null) {
					l.push(o)
				}
			}
		}
		return l.length > 0 ? [].concat.apply([], l) : l
	};
	e.each = function(m, n) {
		if(!m) {
			return this
		}
		if(typeof m.length === "number") {
			[].every.call(m, function(p, o) {
				return n.call(p, o, p) !== false
			})
		} else {
			for(var l in m) {
				if(n.call(m[l], l, m[l]) === false) {
					return m
				}
			}
		}
		return this
	};
	e.focus = function(l) {
		if(e.os.ios) {
			setTimeout(function() {
				l.focus()
			}, 10)
		} else {
			l.focus()
		}
	};
	e.trigger = function(m, l, n) {
		m.dispatchEvent(new CustomEvent(l, {
			detail: n,
			bubbles: true,
			cancelable: true
		}));
		return this
	};
	e.getStyles = function(l, n) {
		var m = l.ownerDocument.defaultView.getComputedStyle(l, null);
		if(n) {
			return m.getPropertyValue(n) || m[n]
		}
		return m
	};
	e.parseTranslate = function(n, m) {
		var l = n.match(a || "");
		if(!l || !l[1]) {
			l = ["", "0,0,0"]
		}
		l = l[1].split(",");
		l = {
			x: parseFloat(l[0]),
			y: parseFloat(l[1]),
			z: parseFloat(l[2])
		};
		if(m && l.hasOwnProperty(m)) {
			return l[m]
		}
		return l
	};
	e.parseTranslateMatrix = function(p, m) {
		var n = p.match(k);
		var o = n && n[1];
		if(n) {
			n = n[2].split(",");
			if(o === "3d") {
				n = n.slice(12, 15)
			} else {
				n.push(0);
				n = n.slice(4, 7)
			}
		} else {
			n = [0, 0, 0]
		}
		var l = {
			x: parseFloat(n[0]),
			y: parseFloat(n[1]),
			z: parseFloat(n[2])
		};
		if(m && l.hasOwnProperty(m)) {
			return l[m]
		}
		return l
	};
	e.registerHandler = function(n, m) {
		var l = e[n];
		if(!l) {
			l = []
		}
		m.index = m.index || 1000;
		l.push(m);
		l.sort(function(p, o) {
			return p.index - o.index
		});
		e[n] = l;
		return e[n]
	};
	e.later = function(p, n, o, t) {
		n = n || 0;
		var l = p;
		var u = t;
		var s;
		var q;
		if(typeof p === "string") {
			l = o[p]
		}
		s = function() {
			l.apply(o, e.isArray(u) ? u : [u])
		};
		q = setTimeout(s, n);
		return {
			id: q,
			cancel: function() {
				clearTimeout(q)
			}
		}
	};
	e.now = Date.now || function() {
		return +new Date()
	};
	var i = {};
	e.each(["Boolean", "Number", "String", "Function", "Array", "Date", "RegExp", "Object", "Error"], function(m, l) {
		i["[object " + l + "]"] = l.toLowerCase()
	});
	if(window.JSON) {
		e.parseJSON = JSON.parse
	}
	e.fn = {
		each: function(l) {
			[].every.call(this, function(n, m) {
				return l.call(n, m, n) !== false
			});
			return this
		}
	};
	if(typeof define === "function" && define.amd) {
		define("mui", [], function() {
			return e
		})
	}
	return e
})(document);
(function(c, b) {
	function a(e) {
		this.os = {};
		var d = [function() {
			var f = e.match(/(Android);?[\s\/]+([\d.]+)?/);
			if(f) {
				this.os.android = true;
				this.os.version = f[2];
				this.os.isBadAndroid = !(/Chrome\/\d/.test(b.navigator.appVersion))
			}
			return this.os.android === true
		}, function() {
			var g = e.match(/(iPhone\sOS)\s([\d_]+)/);
			if(g) {
				this.os.ios = this.os.iphone = true;
				this.os.version = g[2].replace(/_/g, ".")
			} else {
				var f = e.match(/(iPad).*OS\s([\d_]+)/);
				if(f) {
					this.os.ios = this.os.ipad = true;
					this.os.version = f[2].replace(/_/g, ".")
				}
			}
			return this.os.ios === true
		}];
		[].every.call(d, function(f) {
			return !f.call(c)
		})
	}
	a.call(c, navigator.userAgent)
})(mui, window);
(function(b) {
	function a(c) {
		this.os = this.os || {};
		var d = c.match(/Html5Plus/i);
		if(d) {
			this.os.plus = true
		}
	}
	a.call(b, navigator.userAgent)
})(mui);
(function(c, b, a) {
	c.targets = {};
	c.targetHandles = [];
	c.registerTarget = function(d) {
		d.index = d.index || 1000;
		c.targetHandles.push(d);
		c.targetHandles.sort(function(f, e) {
			return f.index - e.index
		});
		return c.targetHandles
	};
	b.addEventListener("touchstart", function(e) {
		var f = e.target;
		var d = {};
		for(; f && f !== a; f = f.parentNode) {
			var g = false;
			c.each(c.targetHandles, function(i, j) {
				var h = j.name;
				if(!g && !d[h] && j.hasOwnProperty("handle")) {
					c.targets[h] = j.handle(e, f);
					if(c.targets[h]) {
						d[h] = true;
						if(j.isContinue !== true) {
							g = true
						}
					}
				} else {
					if(!d[h]) {
						if(j.isReset !== false) {
							c.targets[h] = false
						}
					}
				}
			});
			if(g) {
				break
			}
		}
	})
})(mui, window, document);
(function(a) {
	if(String.prototype.trim === a) {
		String.prototype.trim = function() {
			return this.replace(/^\s+|\s+$/g, "")
		}
	}
	Object.setPrototypeOf = Object.setPrototypeOf || function(c, b) {
		c.__proto__ = b;
		return c
	}
})();
(function() {
	if(typeof window.CustomEvent === "undefined") {
		function a(e, f) {
			f = f || {
				bubbles: false,
				cancelable: false,
				detail: undefined
			};
			var c = document.createEvent("Events");
			var b = true;
			for(var d in f) {
				(d === "bubbles") ? (b = !!f[d]) : (c[d] = f[d])
			}
			c.initEvent(e, b, true);
			return c
		}
		a.prototype = window.Event.prototype;
		window.CustomEvent = a
	}
})();
(function(a) {
	if(!("classList" in a.documentElement) && Object.defineProperty && typeof HTMLElement !== "undefined") {
		Object.defineProperty(HTMLElement.prototype, "classList", {
			get: function() {
				var b = this;

				function d(e) {
					return function(h) {
						var g = b.className.split(/\s+/),
							f = g.indexOf(h);
						e(g, f, h);
						b.className = g.join(" ")
					}
				}
				var c = {
					add: d(function(f, e, g) {
						~e || f.push(g)
					}),
					remove: d(function(f, e) {
						~e && f.splice(e, 1)
					}),
					toggle: d(function(f, e, g) {
						~e ? f.splice(e, 1) : f.push(g)
					}),
					contains: function(e) {
						return !!~b.className.split(/\s+/).indexOf(e)
					},
					item: function(e) {
						return b.className.split(/\s+/)[e] || null
					}
				};
				Object.defineProperty(c, "length", {
					get: function() {
						return b.className.split(/\s+/).length
					}
				});
				return c
			}
		})
	}
})(document);
(function(a) {
	if(!a.requestAnimationFrame) {
		var b = 0;
		a.requestAnimationFrame = a.webkitRequestAnimationFrame || function(g, d) {
			var c = new Date().getTime();
			var e = Math.max(0, 16.7 - (c - b));
			var f = a.setTimeout(function() {
				g(c + e)
			}, e);
			b = c + e;
			return f
		};
		a.cancelAnimationFrame = a.webkitCancelAnimationFrame || a.webkitCancelRequestAnimationFrame || function(c) {
			clearTimeout(c)
		}
	}
}(window));
(function(d, b, a) {
	if(b.FastClick) {
		return
	}
	var c = function(f, g) {
		if(g.tagName === "LABEL") {
			if(g.parentNode) {
				g = g.parentNode.querySelector("input")
			}
		}
		if(g && (g.type === "radio" || g.type === "checkbox")) {
			if(!g.disabled) {
				return g
			}
		}
		return false
	};
	d.registerTarget({
		name: a,
		index: 40,
		handle: c,
		target: false
	});
	var e = function(h) {
		var g = d.targets.click;
		if(g) {
			var f, i;
			if(document.activeElement && document.activeElement !== g) {
				document.activeElement.blur()
			}
			i = h.detail.gesture.changedTouches[0];
			f = document.createEvent("MouseEvents");
			f.initMouseEvent("click", true, true, b, 1, i.screenX, i.screenY, i.clientX, i.clientY, false, false, false, false, 0, null);
			f.forwardedTouchEvent = true;
			g.dispatchEvent(f)
		}
	};
	b.addEventListener("tap", e);
	b.addEventListener("doubletap", e);
	b.addEventListener("click", function(f) {
		if(d.targets.click) {
			if(!f.forwardedTouchEvent) {
				if(f.stopImmediatePropagation) {
					f.stopImmediatePropagation()
				} else {
					f.propagationStopped = true
				}
				f.stopPropagation();
				f.preventDefault();
				return false
			}
		}
	}, true)
})(mui, window, "click");
(function(b, a) {
	b(function() {
		if(!b.os.ios) {
			return
		}
		var c = "mui-focusin";
		var g = "mui-bar-tab";
		var d = "mui-bar-footer";
		var e = "mui-bar-footer-secondary";
		var f = "mui-bar-footer-secondary-tab";
		a.addEventListener("focusin", function(k) {
			if(b.os.plus) {
				if(window.plus) {
					if(plus.webview.currentWebview().children().length > 0) {
						return
					}
				}
			}
			var j = k.target;
			if(j.tagName && j.tagName === "INPUT" && j.type === "text") {
				if(j.disabled || j.readOnly) {
					return
				}
				a.body.classList.add(c);
				var h = false;
				for(; j && j !== a; j = j.parentNode) {
					var m = j.classList;
					if(m && m.contains(g) || m.contains(d) || m.contains(e) || m.contains(f)) {
						h = true;
						break
					}
				}
				if(h) {
					var i = a.body.scrollHeight;
					var l = a.body.scrollLeft;
					setTimeout(function() {
						window.scrollTo(l, i)
					}, 20)
				}
			}
		});
		a.addEventListener("focusout", function(h) {
			var i = a.body.classList;
			if(i.contains(c)) {
				i.remove(c);
				setTimeout(function() {
					window.scrollTo(a.body.scrollLeft, a.body.scrollTop)
				}, 20)
			}
		})
	})
})(mui, document);
(function(a) {
	a.namespace = "mui";
	a.classNamePrefix = a.namespace + "-";
	a.classSelectorPrefix = "." + a.classNamePrefix;
	a.className = function(b) {
		return a.classNamePrefix + b
	};
	a.classSelector = function(b) {
		return b.replace(/\./g, a.classSelectorPrefix)
	};
	a.eventName = function(c, b) {
		return c + (a.namespace ? ("." + a.namespace) : "") + (b ? ("." + b) : "")
	}
})(mui);
(function(d, g) {
	d.EVENT_START = "touchstart";
	d.EVENT_MOVE = "touchmove";
	d.EVENT_END = "touchend";
	d.EVENT_CANCEL = "touchcancel";
	d.EVENT_CLICK = "click";
	d.preventDefault = function(l) {
		l.preventDefault()
	};
	d.stopPropagation = function(l) {
		l.stopPropagation()
	};
	d.registerGesture = function(l) {
		return d.registerHandler("gestures", l)
	};
	var e = function(n, m) {
		var l = m.x - n.x;
		var o = m.y - n.y;
		return Math.sqrt((l * l) + (o * o))
	};
	var j = function(m, l) {
		return Math.atan2(l.y - m.y, l.x - m.x) * 180 / Math.PI
	};
	var a = function(l) {
		if(l < -45 && l > -135) {
			return "up"
		} else {
			if(l >= 45 && l < 135) {
				return "down"
			} else {
				if(l >= 135 || l <= -135) {
					return "left"
				} else {
					if(l >= -45 && l <= 45) {
						return "right"
					}
				}
			}
		}
		return null
	};
	var b = function(l, m) {
		if(d.gestures.stoped) {
			return
		}
		d.each(d.gestures, function(n, o) {
			if(!d.gestures.stoped) {
				if(d.options.gestureConfig[o.name] !== false) {
					o.handle(l, m)
				}
			}
		})
	};
	var h = function(n) {
		d.gestures.stoped = false;
		var m = d.now();
		var l = n.touches ? n.touches[0] : n;
		d.gestures.touch = {
			target: n.target,
			lastTarget: (d.gestures.touch && d.gestures.touch.lastTarget ? d.gestures.touch.lastTarget : null),
			startTime: m,
			touchTime: 0,
			flickStartTime: m,
			lastTapTime: (d.gestures.touch && d.gestures.touch.lastTapTime ? d.gestures.touch.lastTapTime : 0),
			start: {
				x: l.pageX,
				y: l.pageY
			},
			flickStart: {
				x: l.pageX,
				y: l.pageY
			},
			flickDistanceX: 0,
			flickDistanceY: 0,
			move: {
				x: 0,
				y: 0
			},
			deltaX: 0,
			deltaY: 0,
			lastDeltaX: 0,
			lastDeltaY: 0,
			angle: "",
			direction: "",
			lockDirection: false,
			startDirection: "",
			distance: 0,
			drag: false,
			swipe: false,
			hold: false,
			gesture: n
		};
		b(n, d.gestures.touch)
	};
	var k = function(n) {
		if(d.gestures.stoped) {
			return
		}
		var o = d.gestures.touch;
		if(n.target != o.target) {
			return
		}
		var m = d.now();
		var l = n.touches ? n.touches[0] : n;
		o.touchTime = m - o.startTime;
		o.move = {
			x: l.pageX,
			y: l.pageY
		};
		if(m - o.flickStartTime > 300) {
			o.flickStartTime = m;
			o.flickStart = o.move
		}
		o.distance = e(o.start, o.move);
		o.angle = j(o.start, o.move);
		o.direction = a(o.angle);
		o.lastDeltaX = o.deltaX;
		o.lastDeltaY = o.deltaY;
		o.deltaX = o.move.x - o.start.x;
		o.deltaY = o.move.y - o.start.y;
		o.gesture = n;
		b(n, o)
	};
	var i = function(m) {
		if(d.gestures.stoped) {
			return
		}
		var n = d.gestures.touch;
		if(m.target != n.target) {
			return
		}
		var l = d.now();
		n.touchTime = l - n.startTime;
		n.flickTime = l - n.flickStartTime;
		n.flickDistanceX = n.move.x - n.flickStart.x;
		n.flickDistanceY = n.move.y - n.flickStart.y;
		n.gesture = m;
		b(m, n)
	};
	g.addEventListener(d.EVENT_START, h);
	g.addEventListener(d.EVENT_MOVE, k);
	g.addEventListener(d.EVENT_END, i);
	g.addEventListener(d.EVENT_CANCEL, i);
	g.addEventListener(d.EVENT_CLICK, function(l) {
		if((d.targets.popover && l.target === d.targets.popover) || (d.targets.tab) || d.targets.offcanvas || d.targets.modal) {
			l.preventDefault()
		}
	}, true);
	d.fn.on = function(m, l, n) {
		return this.each(function() {
			var o = this;
			o.addEventListener(m, function(r) {
				var p = d.qsa(l, o);
				var q = r.target;
				if(p && p.length > 0) {
					for(; q && q !== document; q = q.parentNode) {
						if(q === o) {
							break
						}
						if(q && ~p.indexOf(q)) {
							if(~["click", "tap", "doubletap", "longtap", "hold"].indexOf(m) && (q.disabled || q.classList.contains("mui-disabled"))) {
								break
							}
							if(!r.detail) {
								r.detail = {
									currentTarget: q
								}
							} else {
								r.detail.currentTarget = q
							}
							n.call(q, r)
						}
					}
				}
			});
			if(m === "tap") {
				o.removeEventListener(d.EVENT_CLICK, f);
				o.addEventListener(d.EVENT_CLICK, f)
			}
		})
	};
	var f = function(m) {
		var l = m.target && m.target.tagName;
		if(l !== "INPUT" && l !== "TEXTAREA" && l !== "SELECT") {
			m.preventDefault()
		}
	};
	d.isScrolling = false;
	var c = null;
	g.addEventListener("scroll", function() {
		d.isScrolling = true;
		c && clearTimeout(c);
		c = setTimeout(function() {
			d.isScrolling = false
		}, 250)
	})
})(mui, window);
(function(c, a) {
	var b = function(e, f) {
		if(e.type === c.EVENT_END || e.type === c.EVENT_CANCEL) {
			var d = this.options;
			if(f.direction && d.flickMaxTime > f.flickTime && f.distance > d.flickMinDistince) {
				f.flick = true;
				c.trigger(e.target, a, f);
				c.trigger(e.target, a + f.direction, f)
			}
		}
	};
	c.registerGesture({
		name: a,
		index: 5,
		handle: b,
		options: {
			flickMaxTime: 200,
			flickMinDistince: 10
		}
	})
})(mui, "flick");
(function(c, a) {
	var b = function(e, f) {
		if(e.type === c.EVENT_END || e.type === c.EVENT_CANCEL) {
			var d = this.options;
			if(f.direction && d.swipeMaxTime > f.touchTime && f.distance > d.swipeMinDistince) {
				f.swipe = true;
				c.trigger(e.target, a + f.direction, f)
			}
		}
	};
	c.registerGesture({
		name: a,
		index: 10,
		handle: b,
		options: {
			swipeMaxTime: 300,
			swipeMinDistince: 18
		}
	})
})(mui, "swipe");
(function(c, a) {
	var b = function(d, e) {
		switch(d.type) {
			case c.EVENT_MOVE:
				if(e.direction) {
					if(e.lockDirection && e.startDirection) {
						if(e.startDirection && e.startDirection !== e.direction) {
							if(e.startDirection === "up" || e.startDirection === "down") {
								e.direction = e.deltaY < 0 ? "up" : "down"
							} else {
								e.direction = e.deltaX < 0 ? "left" : "right"
							}
						}
					}
					if(!e.drag) {
						e.drag = true;
						c.trigger(d.target, a + "start", e)
					}
					c.trigger(d.target, a, e);
					c.trigger(d.target, a + e.direction, e)
				}
				break;
			case c.EVENT_END:
			case c.EVENT_CANCEL:
				if(e.drag) {
					c.trigger(d.target, a + "end", e)
				}
				break
		}
	};
	c.registerGesture({
		name: a,
		index: 20,
		handle: b,
		options: {}
	})
})(mui, "drag");
(function(c, a) {
	var b = function(e, f) {
		if(e.type === c.EVENT_END) {
			var d = this.options;
			if(f.distance < d.tapMaxDistance && f.touchTime < d.tapMaxTime) {
				if(c.options.gestureConfig.doubletap && f.lastTarget && (f.lastTarget === e.target)) {
					if(f.lastTapTime && (f.startTime - f.lastTapTime) < d.tapMaxInterval) {
						c.trigger(e.target, "doubletap", f);
						f.lastTapTime = c.now();
						f.lastTarget = e.target;
						return
					}
				}
				c.trigger(e.target, a, f);
				f.lastTapTime = c.now();
				f.lastTarget = e.target
			}
		}
	};
	c.registerGesture({
		name: a,
		index: 30,
		handle: b,
		options: {
			tapMaxInterval: 300,
			tapMaxDistance: 5,
			tapMaxTime: 250
		}
	})
})(mui, "tap");
(function(c, a) {
	var d;
	var b = function(f, g) {
		var e = this.options;
		switch(f.type) {
			case c.EVENT_START:
				clearTimeout(d);
				d = setTimeout(function() {
					if(!g.drag) {
						c.trigger(f.target, a, g)
					}
				}, e.holdTimeout);
				break;
			case c.EVENT_MOVE:
				if(g.distance > e.holdThreshold) {
					clearTimeout(d)
				}
				break;
			case c.EVENT_END:
			case c.EVENT_CANCEL:
				clearTimeout(d);
				break
		}
	};
	c.registerGesture({
		name: a,
		index: 10,
		handle: b,
		options: {
			holdTimeout: 500,
			holdThreshold: 2
		}
	})
})(mui, "longtap");
(function(c, a) {
	var d;
	var b = function(f, g) {
		var e = this.options;
		switch(f.type) {
			case c.EVENT_START:
				clearTimeout(d);
				d = setTimeout(function() {
					g.hold = true;
					c.trigger(f.target, a, g)
				}, e.holdTimeout);
				break;
			case c.EVENT_MOVE:
				break;
			case c.EVENT_END:
			case c.EVENT_CANCEL:
				clearTimeout(d);
				if(c.options.gestureConfig.hold && g.hold) {
					c.trigger(f.target, "release", g)
				}
				break
		}
	};
	c.registerGesture({
		name: a,
		index: 10,
		handle: b,
		options: {
			holdTimeout: 0
		}
	})
})(mui, "hold");
(function(c) {
	c.global = c.options = {
		gestureConfig: {
			tap: true,
			doubletap: false,
			longtap: false,
			hold: false,
			flick: true,
			swipe: true,
			drag: true
		}
	};
	c.initGlobal = function(d) {
		c.options = c.extend(true, c.global, d);
		return this
	};
	var b = {};
	var a = false;
	c.init = function(d) {
		a = true;
		c.options = c.extend(true, c.global, d || {});
		c.ready(function() {
			c.each(c.inits, function(e, f) {
				var g = !!(!b[f.name] || f.repeat);
				if(g) {
					f.handle.call(c);
					b[f.name] = true
				}
			})
		});
		return this
	};
	c.registerInit = function(d) {
		return c.registerHandler("inits", d)
	};
	c(function() {
		var g = document.body.classList;
		var f = "";
		if(c.os.ios) {
			f = "ios";
			g.add("mui-ios")
		} else {
			if(c.os.android) {
				f = "android";
				g.add("mui-android")
			}
		}
		if(f && c.os.version) {
			var e = "";
			var d = [];
			c.each(c.os.version.split("."), function(j, h) {
				e = e + (e ? "-" : "") + h;
				g.add(c.className(f + "-" + e))
			})
		}
	})
})(mui);
(function(d) {
	var a = {
		swipeBack: false,
		preloadPages: [],
		preloadLimit: 10,
		keyEventBind: {
			backbutton: false,
			menubutton: false
		}
	};
	var c = {
		autoShow: true,
		duration: d.os.ios ? 200 : 100,
		aniShow: "slide-in-right"
	};
	if(d.options.show) {
		c = d.extend(true, c, d.options.show)
	}
	d.currentWebview = null;
	d.isHomePage = false;
	d.extend(true, d.global, a);
	d.extend(true, d.options, a);
	d.waitingOptions = function(f) {
		return d.extend({
			autoShow: true,
			title: ""
		}, f)
	};
	d.showOptions = function(f) {
		return d.extend(c, f)
	};
	d.windowOptions = function(f) {
		return d.extend({
			scalable: false,
			bounce: ""
		}, f)
	};
	d.plusReady = function(f) {
		if(window.plus) {
			f()
		} else {
			document.addEventListener("plusready", function() {
				f()
			}, false)
		}
		return this
	};
	d.fire = function(f, g, h) {
		if(f) {
			f.evalJS("typeof mui!=='undefined'&&mui.receive('" + g + "','" + JSON.stringify(h || {}).replace(/\'/g, "\\u0027").replace(/\\/g, "\\u005c") + "')")
		}
	};
	d.receive = function(f, g) {
		if(f) {
			g = JSON.parse(g);
			d.trigger(document, f, g)
		}
	};
	var e = function(f) {
		if(!f.preloaded) {
			d.fire(f, "preload");
			var h = f.children();
			for(var g = 0; g < h.length; g++) {
				d.fire(h[g], "preload")
			}
			f.preloaded = true
		}
	};
	var b = function(f, h, k) {
		if(k) {
			if(!f[h + "ed"]) {
				d.fire(f, h);
				var j = f.children();
				for(var g = 0; g < j.length; g++) {
					d.fire(j[g], h)
				}
				f[h + "ed"] = true
			}
		} else {
			d.fire(f, h);
			var j = f.children();
			for(var g = 0; g < j.length; g++) {
				d.fire(j[g], h)
			}
		}
	};
	d.openWindow = function(f, g, n) {
		if(!window.plus) {
			return
		}
		if(typeof f === "object") {
			n = f;
			f = n.url;
			g = n.id || f
		} else {
			if(typeof g === "object") {
				n = g;
				g = f
			} else {
				g = g || f
			}
		}
		n = n || {};
		var h = n.params || {};
		var l, k, m;
		if(d.webviews[g]) {
			var i = d.webviews[g];
			l = i.webview;
			if(!l || !l.getURL()) {
				n = d.extend(n, {
					id: g,
					url: f,
					preload: true
				}, true);
				l = d.createWindow(n)
			}
			k = i.show;
			k = n.show ? d.extend(k, n.show) : k;
			l.show(k.aniShow, k.duration, function() {
				e(l);
				b(l, "pagebeforeshow", false)
			});
			i.afterShowMethodName && l.evalJS(i.afterShowMethodName + "('" + JSON.stringify(h) + "')");
			return l
		} else {
			var j = d.waitingOptions(n.waiting);
			if(j.autoShow) {
				m = plus.nativeUI.showWaiting(j.title, j.options)
			}
			n = d.extend(n, {
				id: g,
				url: f
			});
			l = d.createWindow(n);
			k = d.showOptions(n.show);
			if(k.autoShow) {
				l.addEventListener("loaded", function() {
					if(m) {
						m.close()
					}
					l.show(k.aniShow, k.duration, function() {
						e(l);
						b(l, "pagebeforeshow", false)
					});
					l.showed = true;
					n.afterShowMethodName && l.evalJS(n.afterShowMethodName + "('" + JSON.stringify(h) + "')")
				}, false)
			}
		}
		return l
	};
	d.createWindow = function(i, g) {
		if(!window.plus) {
			return
		}
		var m = i.id || i.url;
		var f;
		if(i.preload) {
			if(d.webviews[m] && d.webviews[m].webview.getURL()) {
				f = d.webviews[m].webview
			} else {
				f = plus.webview.create(i.url, m, d.windowOptions(i.styles), d.extend({
					preload: true
				}, i.extras));
				if(i.subpages) {
					d.each(i.subpages, function(o, n) {
						var p = plus.webview.create(n.url, n.id || n.url, d.windowOptions(n.styles), d.extend({
							preload: true
						}, n.extras));
						f.append(p)
					})
				}
			}
			d.webviews[m] = {
				webview: f,
				preload: true,
				show: d.showOptions(i.show),
				afterShowMethodName: i.afterShowMethodName
			};
			var k = d.data.preloads;
			var h = k.indexOf(m);
			if(~h) {
				k.splice(h, 1)
			}
			k.push(m);
			if(k.length > d.options.preloadLimit) {
				var l = d.data.preloads.shift();
				var j = d.webviews[l];
				if(j && j.webview) {
					d.closeAll(j.webview)
				}
				delete d.webviews[l]
			}
		} else {
			if(g !== false) {
				f = plus.webview.create(i.url, m, d.windowOptions(i.styles), i.extras);
				if(i.subpages) {
					d.each(i.subpages, function(o, n) {
						var p = plus.webview.create(n.url, n.id || n.url, d.windowOptions(n.styles), n.extras);
						f.append(p)
					})
				}
			}
		}
		return f
	};
	d.preload = function(f) {
		if(!f.preload) {
			f.preload = true
		}
		return d.createWindow(f)
	};
	d.closeOpened = function(g) {
		var l = g.opened();
		if(l) {
			for(var h = 0, f = l.length; h < f; h++) {
				var k = l[h];
				var j = k.opened();
				if(j && j.length > 0) {
					d.closeOpened(k)
				} else {
					if(k.parent() !== g) {
						k.close("none")
					}
				}
			}
		}
	};
	d.closeAll = function(f, g) {
		d.closeOpened(f);
		if(g) {
			f.close(g)
		} else {
			f.close()
		}
	};
	d.createWindows = function(f) {
		d.each(f, function(g, h) {
			d.createWindow(h, false)
		})
	};
	d.appendWebview = function(g) {
		if(!window.plus) {
			return
		}
		var h = g.id || g.url;
		var f;
		if(!d.webviews[h]) {
			f = plus.webview.create(g.url, h, g.styles, g.extras);
			f.addEventListener("loaded", function() {
				plus.webview.currentWebview().append(f)
			});
			d.webviews[h] = g
		}
		return f
	};
	d.webviews = {};
	d.data.preloads = [];
	d.plusReady(function() {
		d.currentWebview = plus.webview.currentWebview()
	});
	d.registerInit({
		name: "5+",
		index: 100,
		handle: function() {
			var g = d.options;
			var i = g.subpages || [];
			if(d.os.plus) {
				d.plusReady(function() {
					d.each(i, function(l, k) {
						d.appendWebview(k)
					});
					if(plus.webview.currentWebview() === plus.webview.getWebviewById(plus.runtime.appid)) {
						d.isHomePage = true;
						setTimeout(function() {
							e(plus.webview.currentWebview())
						}, 300)
					}
					if(d.os.ios && d.options.statusBarBackground) {
						plus.navigator.setStatusBarBackground(d.options.statusBarBackground)
					}
					if(d.os.android && parseFloat(d.os.version) < 4.4) {
						if(plus.webview.currentWebview().parent() == null) {
							document.addEventListener("resume", function() {
								var k = document.body;
								k.style.display = "none";
								setTimeout(function() {
									k.style.display = ""
								}, 10)
							})
						}
					}
				})
			} else {
				if(i.length > 0) {
					var j = document.createElement("div");
					j.className = "mui-error";
					var h = document.createElement("span");
					h.innerHTML = "在该浏览器下，不支持创建子页面，具体参考";
					j.appendChild(h);
					var f = document.createElement("a");
					f.innerHTML = '"mui框架适用场景"';
					f.href = "http://ask.dcloud.net.cn/article/113";
					j.appendChild(f);
					document.body.appendChild(j);
					console.log("在该浏览器下，不支持创建子页面")
				}
			}
		}
	});
	window.addEventListener("preload", function() {
		var f = d.options.preloadPages || [];
		d.plusReady(function() {
			d.each(f, function(h, g) {
				d.createWindow(d.extend(g, {
					preload: true
				}))
			})
		})
	})
})(mui);
(function(b, a) {
	b.registerBack = function(c) {
		return b.registerHandler("backs", c)
	};
	b.registerBack({
		name: "browser",
		index: 100,
		handle: function() {
			if(a.history.length > 1) {
				a.history.back();
				return true
			}
			return false
		}
	});
	b.back = function() {
		if(typeof b.options.beforeback === "function") {
			if(b.options.beforeback() === false) {
				return
			}
		}
		b.each(b.backs, function(d, c) {
			return !c.handle()
		})
	};
	a.addEventListener("tap", function(d) {
		var c = b.targets.action;
		if(c && c.classList.contains("mui-action-back")) {
			b.back()
		}
	});
	a.addEventListener("swiperight", function(d) {
		var c = d.detail;
		if(b.options.swipeBack === true && Math.abs(c.angle) < 3) {
			b.back()
		}
	})
})(mui, window);
(function(b, a) {
	if(b.os.plus && b.os.android) {
		b.registerBack({
			name: "mui",
			index: 5,
			handle: function() {
				if(b.targets._popover) {
					b(b.targets._popover).popover("hide");
					return true
				}
				var c = document.querySelector(".mui-off-canvas-wrap.mui-active");
				if(c) {
					b(c).offCanvas("close");
					return true
				}
			}
		})
	}
	b.registerBack({
		name: "5+",
		index: 10,
		handle: function() {
			if(!a.plus) {
				return false
			}
			var c = plus.webview.currentWebview();
			var d = c.parent();
			if(d) {
				d.evalJS("mui&&mui.back();")
			} else {
				c.canBack(function(g) {
					if(g.canBack) {
						a.history.back()
					} else {
						var f = c.opener();
						if(f) {
							if(c.preload) {
								c.hide("auto")
							} else {
								b.closeAll(c)
							}
						} else {
							plus.ehomev5.closeWebview(c.id)
						}
					}
				})
			}
			return true
		}
	});
	b.menu = function() {
		var e = document.querySelector(".mui-action-menu");
		if(e) {
			b.trigger(e, "touchstart");
			b.trigger(e, "tap")
		} else {
			if(a.plus) {
				var c = b.currentWebview;
				var d = c.parent();
				if(d) {
					d.evalJS("mui&&mui.menu();")
				}
			}
		}
	};
	b.plusReady(function() {
		if(b.options.keyEventBind.backbutton) {
			plus.key.addEventListener("backbutton", b.back, false)
		}
		if(b.options.keyEventBind.menubutton) {
			plus.key.addEventListener("menubutton", b.menu, false)
		}
	});
	b.registerInit({
		name: "keyEventBind",
		index: 1000,
		handle: function() {
			b.plusReady(function() {
				if(!b.options.keyEventBind.backbutton) {
					plus.key.removeEventListener("backbutton", b.back)
				}
				if(!b.options.keyEventBind.menubutton) {
					plus.key.removeEventListener("menubutton", b.menu)
				}
			})
		}
	})
})(mui, window);
(function(a) {
	a.registerInit({
		name: "pullrefresh",
		index: 1000,
		handle: function() {
			var d = a.options;
			var e = d.pullRefresh || {};
			var c = e.down && e.down.hasOwnProperty("callback");
			var f = e.up && e.up.hasOwnProperty("callback");
			if(c || f) {
				var b = e.container;
				if(b) {
					var g = a(b);
					if(g.length === 1) {
						if(a.os.plus && a.os.android) {
							a.plusReady(function() {
								var h = plus.webview.currentWebview();
								if(f) {
									var l = {};
									l.up = e.up;
									l.webviewId = h.id || h.getURL();
									g.pullRefresh(l)
								}
								if(c) {
									var j = h.parent();
									var k = h.id || h.getURL();
									if(j) {
										if(!f) {
											g.pullRefresh({
												webviewId: k
											})
										}
										var i = {
											webviewId: k
										};
										i.down = a.extend({}, e.down);
										i.down.callback = "_CALLBACK";
										j.evalJS("mui&&mui(document.querySelector('.mui-content')).pullRefresh('" + JSON.stringify(i) + "')")
									}
								}
							})
						} else {
							g.pullRefresh(e)
						}
					}
				}
			}
		}
	})
})(mui);
(function($, window, undefined) {
	var jsonType = "application/json";
	var htmlType = "text/html";
	var rscript = /<script\b[^<]*(?:(?!<\/script>)<[^<]*)*<\/script>/gi;
	var scriptTypeRE = /^(?:text|application)\/javascript/i;
	var xmlTypeRE = /^(?:text|application)\/xml/i;
	var blankRE = /^\s*$/;
	$.ajaxSettings = {
		type: "GET",
		beforeSend: $.noop,
		success: $.noop,
		error: $.noop,
		complete: $.noop,
		context: null,
		xhr: function(protocol) {
			return new window.XMLHttpRequest()
		},
		accepts: {
			script: "text/javascript, application/javascript, application/x-javascript",
			json: jsonType,
			xml: "application/xml, text/xml",
			html: htmlType,
			text: "text/plain"
		},
		timeout: 0,
		processData: true,
		cache: true
	};
	var ajaxBeforeSend = function(xhr, settings) {
		var context = settings.context;
		if(settings.beforeSend.call(context, xhr, settings) === false) {
			return false
		}
	};
	var ajaxSuccess = function(data, xhr, settings) {
		settings.success.call(settings.context, data, "success", xhr);
		ajaxComplete("success", xhr, settings)
	};
	var ajaxError = function(error, type, xhr, settings) {
		settings.error.call(settings.context, xhr, type, error);
		ajaxComplete(type, xhr, settings)
	};
	var ajaxComplete = function(status, xhr, settings) {
		settings.complete.call(settings.context, xhr, status)
	};
	var serialize = function(params, obj, traditional, scope) {
		var type, array = $.isArray(obj),
			hash = $.isPlainObject(obj);
		$.each(obj, function(key, value) {
			type = $.type(value);
			if(scope) {
				key = traditional ? scope : scope + "[" + (hash || type === "object" || type === "array" ? key : "") + "]"
			}
			if(!scope && array) {
				params.add(value.name, value.value)
			} else {
				if(type === "array" || (!traditional && type === "object")) {
					serialize(params, value, traditional, key)
				} else {
					params.add(key, value)
				}
			}
		})
	};
	var serializeData = function(options) {
		if(options.processData && options.data && typeof options.data !== "string") {
			options.data = $.param(options.data, options.traditional)
		}
		if(options.data && (!options.type || options.type.toUpperCase() === "GET")) {
			options.url = appendQuery(options.url, options.data);
			options.data = undefined
		}
	};
	var appendQuery = function(url, query) {
		if(query === "") {
			return url
		}
		return(url + "&" + query).replace(/[&?]{1,2}/, "?")
	};
	var mimeToDataType = function(mime) {
		if(mime) {
			mime = mime.split(";", 2)[0]
		}
		return mime && (mime === htmlType ? "html" : mime === jsonType ? "json" : scriptTypeRE.test(mime) ? "script" : xmlTypeRE.test(mime) && "xml") || "text"
	};
	var parseArguments = function(url, data, success, dataType) {
		if($.isFunction(data)) {
			dataType = success, success = data, data = undefined
		}
		if(!$.isFunction(success)) {
			dataType = success, success = undefined
		}
		return {
			url: url,
			data: data,
			success: success,
			dataType: dataType
		}
	};
	$.ajax = function(url, options) {
		if(typeof url === "object") {
			options = url;
			url = undefined
		}
		var settings = options || {};
		settings.url = url || settings.url;
		for(var key in $.ajaxSettings) {
			if(settings[key] === undefined) {
				settings[key] = $.ajaxSettings[key]
			}
		}
		serializeData(settings);
		var dataType = settings.dataType;
		if(settings.cache === false || ((!options || options.cache !== true) && ("script" === dataType))) {
			settings.url = appendQuery(settings.url, "_=" + $.now())
		}
		var mime = settings.accepts[dataType];
		var headers = {};
		var setHeader = function(name, value) {
			headers[name.toLowerCase()] = [name, value]
		};
		var protocol = /^([\w-]+:)\/\//.test(settings.url) ? RegExp.$1 : window.location.protocol;
		var xhr = settings.xhr(settings);
		var nativeSetHeader = xhr.setRequestHeader;
		var abortTimeout;
		setHeader("X-Requested-With", "XMLHttpRequest");
		setHeader("Accept", mime || "*/*");
		if(!!(mime = settings.mimeType || mime)) {
			if(mime.indexOf(",") > -1) {
				mime = mime.split(",", 2)[0]
			}
			xhr.overrideMimeType && xhr.overrideMimeType(mime)
		}
		if(settings.contentType || (settings.contentType !== false && settings.data && settings.type.toUpperCase() !== "GET")) {
			setHeader("Content-Type", settings.contentType || "application/x-www-form-urlencoded")
		}
		if(settings.headers) {
			for(var name in settings.headers) {
				setHeader(name, settings.headers[name])
			}
		}
		xhr.setRequestHeader = setHeader;
		xhr.onreadystatechange = function() {
			if(xhr.readyState === 4) {
				xhr.onreadystatechange = $.noop;
				clearTimeout(abortTimeout);
				var result, error = false;
				if((xhr.status >= 200 && xhr.status < 300) || xhr.status === 304 || (xhr.status === 0 && protocol === "file:")) {
					dataType = dataType || mimeToDataType(settings.mimeType || xhr.getResponseHeader("content-type"));
					result = xhr.responseText;
					try {
						if(dataType === "script") {
							(1, eval)(result)
						} else {
							if(dataType === "xml") {
								result = xhr.responseXML
							} else {
								if(dataType === "json") {
									result = blankRE.test(result) ? null : $.parseJSON(result)
								}
							}
						}
					} catch(e) {
						error = e
					}
					if(error) {
						ajaxError(error, "parsererror", xhr, settings)
					} else {
						ajaxSuccess(result, xhr, settings)
					}
				} else {
					ajaxError(xhr.statusText || null, xhr.status ? "error" : "abort", xhr, settings)
				}
			}
		};
		if(ajaxBeforeSend(xhr, settings) === false) {
			xhr.abort();
			ajaxError(null, "abort", xhr, settings);
			return xhr
		}
		if(settings.xhrFields) {
			for(var name in settings.xhrFields) {
				xhr[name] = settings.xhrFields[name]
			}
		}
		var async = "async" in settings ? settings.async : true;
		xhr.open(settings.type.toUpperCase(), settings.url, async, settings.username, settings.password);
		for(var name in headers) {
			nativeSetHeader.apply(xhr, headers[name])
		}
		if(settings.timeout > 0) {
			abortTimeout = setTimeout(function() {
				xhr.onreadystatechange = $.noop;
				xhr.abort();
				ajaxError(null, "timeout", xhr, settings)
			}, settings.timeout)
		}
		xhr.send(settings.data ? settings.data : null);
		return xhr
	};
	$.param = function(obj, traditional) {
		var params = [];
		params.add = function(k, v) {
			this.push(encodeURIComponent(k) + "=" + encodeURIComponent(v))
		};
		serialize(params, obj, traditional);
		return params.join("&").replace(/%20/g, "+")
	};
	$.get = function() {
		return $.ajax(parseArguments.apply(null, arguments))
	};
	$.post = function() {
		var options = parseArguments.apply(null, arguments);
		options.type = "POST";
		return $.ajax(options)
	};
	$.getJSON = function() {
		var options = parseArguments.apply(null, arguments);
		options.dataType = "json";
		return $.ajax(options)
	};
	$.fn.load = function(url, data, success) {
		if(!this.length) {
			return this
		}
		var self = this,
			parts = url.split(/\s/),
			selector, options = parseArguments(url, data, success),
			callback = options.success;
		if(parts.length > 1) {
			options.url = parts[0], selector = parts[1]
		}
		options.success = function(response) {
			if(selector) {
				var div = document.createElement("div");
				div.innerHTML = response.replace(rscript, "");
				var selectorDiv = document.createElement("div");
				var childs = div.querySelectorAll(selector);
				if(childs && childs.length > 0) {
					for(var i = 0, len = childs.length; i < len; i++) {
						selectorDiv.appendChild(childs[i])
					}
				}
				self[0].innerHTML = selectorDiv.innerHTML
			} else {
				self[0].innerHTML = response
			}
			callback && callback.apply(self, arguments)
		};
		$.ajax(options);
		return this
	}
})(mui, window);
(function(b) {
	var a = document.createElement("a");
	a.href = window.location.href;
	b.plusReady(function() {
		b.ajaxSettings = b.extend(b.ajaxSettings, {
			xhr: function(c) {
				if(c.crossDomain) {
					return new plus.net.XMLHttpRequest()
				}
				if(a.protocol !== "file:") {
					var d = document.createElement("a");
					d.href = c.url;
					d.href = d.href;
					c.crossDomain = (a.protocol + "//" + a.host) !== (d.protocol + "//" + d.host);
					if(c.crossDomain) {
						return new plus.net.XMLHttpRequest()
					}
				}
				return new window.XMLHttpRequest()
			}
		})
	})
})(mui);
(function(b, a, c) {
	b.offset = function(d) {
		var e = {
			top: 0,
			left: 0
		};
		if(typeof d.getBoundingClientRect !== c) {
			e = d.getBoundingClientRect()
		}
		return {
			top: e.top + a.pageYOffset - d.clientTop,
			left: e.left + a.pageXOffset - d.clientLeft
		}
	}
})(mui, window);
(function(b, a) {
	b.scrollTo = function(e, d, f) {
		d = d || 1000;
		var c = function(h) {
			if(h <= 0) {
				f && f();
				return
			}
			var g = e - a.scrollY;
			setTimeout(function() {
				a.scrollTo(0, a.scrollY + g / h * 10);
				c(h - 10)
			}, 16.7)
		};
		c(d)
	};
	b.animationFrame = function(c) {
		var d, f, e;
		return function() {
			d = arguments;
			e = this;
			if(!f) {
				f = true;
				requestAnimationFrame(function() {
					c.apply(e, d);
					f = false
				})
			}
		}
	}
})(mui, window);
(function(d) {
	var b = false,
		c = /xyz/.test(function() {
			xyz
		}) ? /\b_super\b/ : /.*/;
	var a = function() {};
	a.extend = function(i) {
		var h = this.prototype;
		b = true;
		var g = new this();
		b = false;
		for(var f in i) {
			g[f] = typeof i[f] == "function" && typeof h[f] == "function" && c.test(i[f]) ? (function(j, k) {
				return function() {
					var m = this._super;
					this._super = h[j];
					var l = k.apply(this, arguments);
					this._super = m;
					return l
				}
			})(f, i[f]) : i[f]
		}

		function e() {
			if(!b && this.init) {
				this.init.apply(this, arguments)
			}
		}
		e.prototype = g;
		e.prototype.constructor = e;
		e.extend = arguments.callee;
		return e
	};
	d.Class = a
})(mui);
(function(c, j, e) {
	var g = "mui-pull-top-pocket";
	var a = "mui-pull-bottom-pocket";
	var s = "mui-pull";
	var b = "mui-pull-loading";
	var q = "mui-pull-caption";
	var r = "mui-icon";
	var k = "mui-spinner";
	var n = "mui-icon-pulldown";
	var o = "mui-block";
	var l = "mui-hidden";
	var m = "mui-visibility";
	var f = b + " " + r + " " + n;
	var d = b + " " + r + " " + n;
	var h = b + " " + r + " " + k;
	var p = ['<div class="' + s + '">', '<div class="{icon}"></div>', '<div class="' + q + '">{contentrefresh}</div>', "</div>"].join("");
	var i = {
		init: function(u, t) {
			this._super(u, c.extend(true, {
				scrollY: true,
				scrollX: false,
				indicators: true,
				down: {
					height: 50,
					contentdown: "下拉可以刷新",
					contentover: "释放立即刷新",
					contentrefresh: "正在刷新..."
				},
				up: {
					height: 50,
					contentdown: "上拉显示更多",
					contentrefresh: "正在加载...",
					contentnomore: "没有更多数据了",
					duration: 300
				}
			}, t))
		},
		_init: function() {
			this._super();
			this._initPocket()
		},
		_initPulldownRefresh: function() {
			this.pulldown = true;
			this.pullPocket = this.topPocket;
			this.pullPocket.classList.add(o);
			this.pullPocket.classList.add(m);
			this.pullCaption = this.topCaption;
			this.pullLoading = this.topLoading
		},
		_initPullupRefresh: function() {
			this.pulldown = false;
			this.pullPocket = this.bottomPocket;
			this.pullPocket.classList.add(o);
			this.pullPocket.classList.add(m);
			this.pullCaption = this.bottomCaption;
			this.pullLoading = this.bottomLoading
		},
		_initPocket: function() {
			var t = this.options;
			if(t.down && t.down.hasOwnProperty("callback")) {
				this.topPocket = this.scroller.querySelector("." + g);
				if(!this.topPocket) {
					this.topPocket = this._createPocket(g, t.down, d);
					this.wrapper.insertBefore(this.topPocket, this.wrapper.firstChild)
				}
				this.topLoading = this.topPocket.querySelector("." + b);
				this.topCaption = this.topPocket.querySelector("." + q)
			}
			if(t.up && t.up.hasOwnProperty("callback")) {
				this.bottomPocket = this.scroller.querySelector("." + a);
				if(!this.bottomPocket) {
					this.bottomPocket = this._createPocket(a, t.up, h);
					this.scroller.appendChild(this.bottomPocket)
				}
				this.bottomLoading = this.bottomPocket.querySelector("." + b);
				this.bottomCaption = this.bottomPocket.querySelector("." + q);
				this.wrapper.addEventListener("scrollbottom", this)
			}
		},
		_createPocket: function(v, u, t) {
			var w = j.createElement("div");
			w.className = v;
			w.innerHTML = p.replace("{contentrefresh}", u.contentrefresh).replace("{icon}", t);
			return w
		},
		_resetPullDownLoading: function() {
			var t = this.pullLoading;
			if(t) {
				this.pullCaption.innerHTML = this.options.down.contentdown;
				t.style.webkitTransition = "";
				t.style.webkitTransform = "";
				t.style.webkitAnimation = "";
				t.className = d
			}
		},
		_setCaption: function(y, x) {
			if(this.loading) {
				return
			}
			var v = this.options;
			var w = this.pullPocket;
			var u = this.pullCaption;
			var z = this.pullLoading;
			var t = this.pulldown;
			if(w) {
				if(x) {
					setTimeout(function() {
						u.innerHTML = y;
						if(t) {
							z.className = d
						} else {
							z.className = h
						}
						z.style.webkitAnimation = "";
						z.style.webkitTransition = "";
						z.style.webkitTransform = ""
					}, 100)
				} else {
					if(y !== this.lastTitle) {
						u.innerHTML = y;
						if(t) {
							if(y === v.down.contentrefresh) {
								z.className = h;
								z.style.webkitAnimation = "spinner-spin 1s step-end infinite"
							} else {
								if(y === v.down.contentover) {
									z.className = f;
									z.style.webkitTransition = "-webkit-transform 0.3s ease-in";
									z.style.webkitTransform = "rotate(180deg)"
								} else {
									if(y === v.down.contentdown) {
										z.className = d;
										z.style.webkitTransition = "-webkit-transform 0.3s ease-in";
										z.style.webkitTransform = "rotate(0deg)"
									}
								}
							}
						} else {
							if(y === v.up.contentrefresh) {
								z.className = h + " " + m
							} else {
								z.className = h + " " + l
							}
						}
						this.lastTitle = y
					}
				}
			}
		}
	};
	c.PullRefresh = i
})(mui, document);
(function(e, g, j, b) {
	var f = "mui-scrollbar";
	var h = "mui-scrollbar-indicator";
	var i = f + "-vertical";
	var d = f + "-horizontal";
	var k = "mui-active";
	var c = {
		quadratic: {
			style: "cubic-bezier(0.25, 0.46, 0.45, 0.94)",
			fn: function(m) {
				return m * (2 - m)
			}
		},
		circular: {
			style: "cubic-bezier(0.1, 0.57, 0.1, 1)",
			fn: function(m) {
				return Math.sqrt(1 - (--m * m))
			}
		}
	};
	var a = e.Class.extend({
		init: function(n, m) {
			this.wrapper = this.element = n;
			this.scroller = this.wrapper.children[0];
			this.scrollerStyle = this.scroller && this.scroller.style;
			this.stopped = false;
			this.options = e.extend(true, {
				scrollY: true,
				scrollX: false,
				startX: 0,
				startY: 0,
				indicators: true,
				stopPropagation: false,
				hardwareAccelerated: true,
				fixedBadAndorid: false,
				preventDefaultException: {
					tagName: /^(INPUT|TEXTAREA|BUTTON|SELECT)$/
				},
				momentum: true,
				snap: false,
				bounce: true,
				bounceTime: 300,
				bounceEasing: c.circular.style,
				directionLockThreshold: 5,
				parallaxElement: false,
				parallaxRatio: 0.5
			}, m);
			this.x = 0;
			this.y = 0;
			this.translateZ = this.options.hardwareAccelerated ? " translateZ(0)" : "";
			this._init();
			if(this.scroller) {
				this.refresh();
				this.scrollTo(this.options.startX, this.options.startY)
			}
		},
		_init: function() {
			this._initParallax();
			this._initIndicators();
			this._initEvent()
		},
		_initParallax: function() {
			if(this.options.parallaxElement) {
				this.parallaxElement = j.querySelector(this.options.parallaxElement);
				this.parallaxStyle = this.parallaxElement.style;
				this.parallaxHeight = this.parallaxElement.offsetHeight;
				this.parallaxImgStyle = this.parallaxElement.querySelector("img").style
			}
		},
		_initIndicators: function() {
			var n = this;
			n.indicators = [];
			if(!this.options.indicators) {
				return
			}
			var p = [],
				m;
			if(n.options.scrollY) {
				m = {
					el: this._createScrollBar(i),
					listenX: false
				};
				this.wrapper.appendChild(m.el);
				p.push(m)
			}
			if(this.options.scrollX) {
				m = {
					el: this._createScrollBar(d),
					listenY: false
				};
				this.wrapper.appendChild(m.el);
				p.push(m)
			}
			for(var o = p.length; o--;) {
				this.indicators.push(new l(this, p[o]))
			}
			this.wrapper.addEventListener("scrollend", function() {
				n.indicators.map(function(q) {
					q.fade()
				})
			});
			this.wrapper.addEventListener("scrollstart", function() {
				n.indicators.map(function(q) {
					q.fade(1)
				})
			});
			this.wrapper.addEventListener("refresh", function() {
				n.indicators.map(function(q) {
					q.refresh()
				})
			})
		},
		_initSnap: function() {
			this.currentPage = {};
			this.pages = [];
			var v = this.snaps;
			var o = v.length;
			var r = 0;
			var p = -1;
			var y = 0;
			var t = 0;
			for(var u = 0; u < o; u++) {
				var s = v[u];
				var q = s.offsetLeft;
				var w = s.offsetWidth;
				if(u === 0 || q <= v[u - 1].offsetLeft) {
					r = 0;
					p++
				}
				if(!this.pages[r]) {
					this.pages[r] = []
				}
				y = this._getSnapX(q);
				t = y - Math.round((w) / 2);
				this.pages[r][p] = {
					x: y,
					cx: t,
					pageX: r,
					element: s
				};
				if(s.classList.contains(k)) {
					this.currentPage = this.pages[r][0]
				}
				if(y >= this.maxScrollX) {
					r++
				}
			}
			this.options.startX = this.currentPage.x || 0
		},
		_getSnapX: function(m) {
			return Math.max(Math.min(0, -m + (this.wrapperWidth / 2)), this.maxScrollX)
		},
		_gotoPage: function(n) {
			this.currentPage = this.pages[Math.min(n, this.pages.length - 1)][0];
			for(var o = 0, m = this.snaps.length; o < m; o++) {
				if(o === n) {
					this.snaps[o].classList.add(k)
				} else {
					this.snaps[o].classList.remove(k)
				}
			}
			this.scrollTo(this.currentPage.x, 0, this.options.bounceTime)
		},
		_nearestSnap: function(m) {
			if(!this.pages.length) {
				return {
					x: 0,
					pageX: 0
				}
			}
			var n = 0;
			var o = this.pages.length;
			if(m > 0) {
				m = 0
			} else {
				if(m < this.maxScrollX) {
					m = this.maxScrollX
				}
			}
			for(; n < o; n++) {
				if(m >= this.pages[n][0].cx) {
					return this.pages[n][0]
				}
			}
			return {
				x: 0,
				pageX: 0
			}
		},
		_initEvent: function() {
			g.addEventListener("orientationchange", this);
			g.addEventListener("resize", this);
			this.scroller.addEventListener("webkitTransitionEnd", this);
			this.wrapper.addEventListener("touchstart", this);
			this.wrapper.addEventListener("touchcancel", this);
			this.wrapper.addEventListener("touchend", this);
			this.wrapper.addEventListener("drag", this);
			this.wrapper.addEventListener("dragend", this);
			this.wrapper.addEventListener("flick", this);
			this.wrapper.addEventListener("scrollend", this);
			if(this.options.scrollX) {
				this.wrapper.addEventListener("swiperight", this)
			}
			var m = this.wrapper.querySelector(".mui-segmented-control");
			if(m) {
				mui(m).on("click", "a", e.preventDefault)
			}
		},
		handleEvent: function(m) {
			if(this.stopped) {
				this.resetPosition();
				return
			}
			switch(m.type) {
				case "touchstart":
					this._start(m);
					break;
				case "drag":
					this.options.stopPropagation && m.stopPropagation();
					this._drag(m);
					break;
				case "dragend":
				case "flick":
					this.options.stopPropagation && m.stopPropagation();
					this._flick(m);
					break;
				case "touchcancel":
				case "touchend":
					this._end(m);
					break;
				case "webkitTransitionEnd":
					this._transitionEnd(m);
					break;
				case "scrollend":
					this._scrollend(m);
					break;
				case "orientationchange":
				case "resize":
					this._resize();
					break;
				case "swiperight":
					m.stopPropagation();
					break
			}
		},
		_start: function(m) {
			this.moved = this.needReset = false;
			this._transitionTime();
			if(this.isInTransition) {
				this.needReset = true;
				this.isInTransition = false;
				var n = e.parseTranslateMatrix(e.getStyles(this.scroller, "webkitTransform"));
				this.setTranslate(Math.round(n.x), Math.round(n.y));
				this.resetPosition();
				e.trigger(this.scroller, "scrollend", this);
				m.preventDefault()
			}
			this.reLayout();
			e.trigger(this.scroller, "beforescrollstart", this)
		},
		_getDirectionByAngle: function(m) {
			if(m < -80 && m > -100) {
				return "up"
			} else {
				if(m >= 80 && m < 100) {
					return "down"
				} else {
					if(m >= 170 || m <= -170) {
						return "left"
					} else {
						if(m >= -35 && m <= 10) {
							return "right"
						}
					}
				}
			}
			return null
		},
		_drag: function(t) {
			var s = t.detail;
			if(this.options.scrollY || s.direction === "up" || s.direction === "down") {
				if(e.os.ios && parseFloat(e.os.version) >= 8) {
					var n = s.gesture.touches[0].clientY;
					if((n + 10) > g.innerHeight || n < 10) {
						this.resetPosition(this.options.bounceTime);
						return
					}
				}
			}
			var u = isReturn = false;
			var v = this._getDirectionByAngle(s.angle);
			if(s.direction === "left" || s.direction === "right") {
				if(this.options.scrollX) {
					u = true;
					if(!this.moved) {
						e.gestures.touch.lockDirection = true;
						e.gestures.touch.startDirection = s.direction
					}
				} else {
					if(this.options.scrollY && !this.moved) {
						isReturn = true
					}
				}
			} else {
				if(s.direction === "up" || s.direction === "down") {
					if(this.options.scrollY) {
						u = true;
						if(!this.moved) {
							e.gestures.touch.lockDirection = true;
							e.gestures.touch.startDirection = s.direction
						}
					} else {
						if(this.options.scrollX && !this.moved) {
							isReturn = true
						}
					}
				} else {
					isReturn = true
				}
			}
			if(u) {
				t.stopPropagation();
				s.gesture && s.gesture.preventDefault()
			}
			if(isReturn) {
				return
			}
			if(!this.moved) {
				e.trigger(this.scroller, "scrollstart", this)
			} else {
				t.stopPropagation()
			}
			var r = 0;
			var q = 0;
			if(!this.moved) {
				r = s.deltaX;
				q = s.deltaY
			} else {
				r = s.deltaX - s.lastDeltaX;
				q = s.deltaY - s.lastDeltaY
			}
			var p = Math.abs(s.deltaX);
			var o = Math.abs(s.deltaY);
			if(p > o + this.options.directionLockThreshold) {
				q = 0
			} else {
				if(o >= p + this.options.directionLockThreshold) {
					r = 0
				}
			}
			r = this.hasHorizontalScroll ? r : 0;
			q = this.hasVerticalScroll ? q : 0;
			var m = this.x + r;
			var w = this.y + q;
			if(m > 0 || m < this.maxScrollX) {
				m = this.options.bounce ? this.x + r / 3 : m > 0 ? 0 : this.maxScrollX
			}
			if(w > 0 || w < this.maxScrollY) {
				w = this.options.bounce ? this.y + q / 3 : w > 0 ? 0 : this.maxScrollY
			}
			if(!this.requestAnimationFrame) {
				this._updateTranslate()
			}
			this.moved = true;
			this.x = m;
			this.y = w;
			e.trigger(this.scroller, "scroll", this)
		},
		_flick: function(p) {
			if(!this.moved) {
				return
			}
			p.stopPropagation();
			var m = p.detail;
			this._clearRequestAnimationFrame();
			if(p.type === "dragend" && m.flick) {
				return
			}
			var q = Math.round(this.x);
			var o = Math.round(this.y);
			this.isInTransition = false;
			if(this.resetPosition(this.options.bounceTime)) {
				return
			}
			this.scrollTo(q, o);
			if(p.type === "dragend") {
				e.trigger(this.scroller, "scrollend", this);
				return
			}
			var n = 0;
			var r = "";
			if(this.options.momentum && m.flickTime < 300) {
				momentumX = this.hasHorizontalScroll ? this._momentum(this.x, m.flickDistanceX, m.flickTime, this.maxScrollX, this.options.bounce ? this.wrapperWidth : 0, this.options.deceleration) : {
					destination: q,
					duration: 0
				};
				momentumY = this.hasVerticalScroll ? this._momentum(this.y, m.flickDistanceY, m.flickTime, this.maxScrollY, this.options.bounce ? this.wrapperHeight : 0, this.options.deceleration) : {
					destination: o,
					duration: 0
				};
				q = momentumX.destination;
				o = momentumY.destination;
				n = Math.max(momentumX.duration, momentumY.duration);
				this.isInTransition = true
			}
			if(q != this.x || o != this.y) {
				if(q > 0 || q < this.maxScrollX || o > 0 || o < this.maxScrollY) {
					r = c.quadratic
				}
				this.scrollTo(q, o, n, r);
				return
			}
			e.trigger(this.scroller, "scrollend", this)
		},
		_end: function(m) {
			this.needReset = false;
			if((!this.moved && this.needReset) || m.type === "touchcancel") {
				this.resetPosition()
			}
		},
		_transitionEnd: function(m) {
			if(m.target != this.scroller || !this.isInTransition) {
				return
			}
			this._transitionTime();
			if(!this.resetPosition(this.options.bounceTime)) {
				this.isInTransition = false;
				e.trigger(this.scroller, "scrollend", this)
			}
		},
		_scrollend: function(m) {
			if(Math.abs(this.y) > 0 && this.y <= this.maxScrollY) {
				e.trigger(this.scroller, "scrollbottom", this)
			}
		},
		_resize: function() {
			var m = this;
			clearTimeout(m.resizeTimeout);
			m.resizeTimeout = setTimeout(function() {
				m.refresh()
			}, m.options.resizePolling)
		},
		_transitionTime: function(n) {
			n = n || 0;
			this.scrollerStyle.webkitTransitionDuration = n + "ms";
			if(this.parallaxElement && this.options.scrollY) {
				this.parallaxStyle.webkitTransitionDuration = n + "ms"
			}
			if(this.options.fixedBadAndorid && !n && e.os.isBadAndroid) {
				this.scrollerStyle.webkitTransitionDuration = "0.001s";
				if(this.parallaxElement && this.options.scrollY) {
					this.parallaxStyle.webkitTransitionDuration = "0.001s"
				}
			}
			if(this.indicators) {
				for(var m = this.indicators.length; m--;) {
					this.indicators[m].transitionTime(n)
				}
			}
		},
		_transitionTimingFunction: function(n) {
			this.scrollerStyle.webkitTransitionTimingFunction = n;
			if(this.parallaxElement && this.options.scrollY) {
				this.parallaxStyle.webkitTransitionDuration = n
			}
			if(this.indicators) {
				for(var m = this.indicators.length; m--;) {
					this.indicators[m].transitionTimingFunction(n)
				}
			}
		},
		_translate: function(m, n) {
			this.x = m;
			this.y = n
		},
		_clearRequestAnimationFrame: function() {
			if(this.requestAnimationFrame) {
				cancelAnimationFrame(this.requestAnimationFrame);
				this.requestAnimationFrame = null
			}
		},
		_updateTranslate: function() {
			var m = this;
			if(m.x !== m.lastX || m.y !== m.lastY) {
				m.setTranslate(m.x, m.y)
			}
			m.requestAnimationFrame = requestAnimationFrame(function() {
				m._updateTranslate()
			})
		},
		_createScrollBar: function(n) {
			var o = j.createElement("div");
			var m = j.createElement("div");
			o.className = f + " " + n;
			m.className = h;
			o.appendChild(m);
			if(n === i) {
				this.scrollbarY = o;
				this.scrollbarIndicatorY = m
			} else {
				if(n === d) {
					this.scrollbarX = o;
					this.scrollbarIndicatorX = m
				}
			}
			this.wrapper.appendChild(o);
			return o
		},
		_preventDefaultException: function(o, n) {
			for(var m in n) {
				if(n[m].test(o[m])) {
					return true
				}
			}
			return false
		},
		_reLayout: function() {
			if(!this.hasHorizontalScroll) {
				this.maxScrollX = 0;
				this.scrollerWidth = this.wrapperWidth
			}
			if(!this.hasVerticalScroll) {
				this.maxScrollY = 0;
				this.scrollerHeight = this.wrapperHeight
			}
			this.indicators.map(function(q) {
				q.refresh()
			});
			if(this.options.snap && typeof this.options.snap === "string") {
				var n = this.scroller.querySelectorAll(this.options.snap);
				this.itemLength = 0;
				this.snaps = [];
				for(var o = 0, m = n.length; o < m; o++) {
					var p = n[o];
					if(p.parentNode === this.scroller) {
						this.itemLength++;
						this.snaps.push(p)
					}
				}
				this._initSnap()
			}
		},
		_momentum: function(r, n, o, m, s, t) {
			var p = parseFloat(Math.abs(n) / o),
				u, q;
			t = t === b ? 0.0006 : t;
			u = r + (p * p) / (2 * t) * (n < 0 ? -1 : 1);
			q = p / t;
			if(u < m) {
				u = s ? m - (s / 2.5 * (p / 8)) : m;
				n = Math.abs(u - r);
				q = n / p
			} else {
				if(u > 0) {
					u = s ? s / 2.5 * (p / 8) : 0;
					n = Math.abs(r) + u;
					q = n / p
				}
			}
			return {
				destination: Math.round(u),
				duration: q
			}
		},
		_getTranslateStr: function(m, n) {
			if(this.options.hardwareAccelerated) {
				return "translate3d(" + m + "px," + n + "px,0px) " + this.translateZ
			}
			return "translate(" + m + "px," + n + "px) "
		},
		setStopped: function(m) {
			this.stopped = !!m
		},
		setTranslate: function(m, q) {
			this.x = m;
			this.y = q;
			this.scrollerStyle.webkitTransform = this._getTranslateStr(m, q);
			if(this.parallaxElement && this.options.scrollY) {
				var n = q * this.options.parallaxRatio;
				var p = 1 + n / ((this.parallaxHeight - n) / 2);
				if(p > 1) {
					this.parallaxImgStyle.opacity = 1 - n / 100 * this.options.parallaxRatio;
					this.parallaxStyle.webkitTransform = this._getTranslateStr(0, -n) + " scale(" + p + "," + p + ")"
				} else {
					this.parallaxImgStyle.opacity = 1;
					this.parallaxStyle.webkitTransform = this._getTranslateStr(0, -1) + " scale(1,1)"
				}
			}
			if(this.indicators) {
				for(var o = this.indicators.length; o--;) {
					this.indicators[o].updatePosition()
				}
			}
			this.lastX = this.x;
			this.lastY = this.y
		},
		reLayout: function() {
			this.wrapper.offsetHeight;
			var n = parseFloat(e.getStyles(this.wrapper, "padding-left")) || 0;
			var r = parseFloat(e.getStyles(this.wrapper, "padding-right")) || 0;
			var o = parseFloat(e.getStyles(this.wrapper, "padding-top")) || 0;
			var p = parseFloat(e.getStyles(this.wrapper, "padding-bottom")) || 0;
			var q = this.wrapper.clientWidth;
			var m = this.wrapper.clientHeight;
			this.scrollerWidth = this.scroller.offsetWidth;
			this.scrollerHeight = this.scroller.offsetHeight;
			this.wrapperWidth = q - n - r;
			this.wrapperHeight = m - o - p;
			this.maxScrollX = Math.min(this.wrapperWidth - this.scrollerWidth, 0);
			this.maxScrollY = Math.min(this.wrapperHeight - this.scrollerHeight, 0);
			this.hasHorizontalScroll = this.options.scrollX && this.maxScrollX < 0;
			this.hasVerticalScroll = this.options.scrollY && this.maxScrollY < 0;
			this._reLayout()
		},
		resetPosition: function(n) {
			var m = this.x,
				o = this.y;
			n = n || 0;
			if(!this.hasHorizontalScroll || this.x > 0) {
				m = 0
			} else {
				if(this.x < this.maxScrollX) {
					m = this.maxScrollX
				}
			}
			if(!this.hasVerticalScroll || this.y > 0) {
				o = 0
			} else {
				if(this.y < this.maxScrollY) {
					o = this.maxScrollY
				}
			}
			if(m == this.x && o == this.y) {
				return false
			}
			this.scrollTo(m, o, n, this.options.bounceEasing);
			return true
		},
		refresh: function() {
			this.reLayout();
			e.trigger(this.scroller, "refresh", this);
			this.resetPosition()
		},
		scrollTo: function(m, p, n, o) {
			var o = o || c.circular;
			this.isInTransition = n > 0 && (this.lastX != m || this.lastY != p);
			if(this.isInTransition) {
				this._clearRequestAnimationFrame();
				this._transitionTimingFunction(o.style);
				this._transitionTime(n);
				this.setTranslate(m, p)
			} else {
				this.setTranslate(m, p)
			}
		},
		scrollToBottom: function(m, n) {
			m = m || this.options.bounceTime;
			this.scrollTo(0, this.maxScrollY, m, n)
		},
		gotoPage: function(m) {
			this._gotoPage(m)
		}
	});
	var l = function(m, n) {
		this.wrapper = typeof n.el == "string" ? j.querySelector(n.el) : n.el;
		this.wrapperStyle = this.wrapper.style;
		this.indicator = this.wrapper.children[0];
		this.indicatorStyle = this.indicator.style;
		this.scroller = m;
		this.options = e.extend({
			listenX: true,
			listenY: true,
			fade: false,
			speedRatioX: 0,
			speedRatioY: 0
		}, n);
		this.sizeRatioX = 1;
		this.sizeRatioY = 1;
		this.maxPosX = 0;
		this.maxPosY = 0;
		if(this.options.fade) {
			this.wrapperStyle.webkitTransform = this.scroller.translateZ;
			this.wrapperStyle.webkitTransitionDuration = this.options.fixedBadAndorid && e.os.isBadAndroid ? "0.001s" : "0ms";
			this.wrapperStyle.opacity = "0"
		}
	};
	l.prototype = {
		handleEvent: function(m) {},
		transitionTime: function(m) {
			m = m || 0;
			this.indicatorStyle.webkitTransitionDuration = m + "ms";
			if(this.scroller.options.fixedBadAndorid && !m && e.os.isBadAndroid) {
				this.indicatorStyle.webkitTransitionDuration = "0.001s"
			}
		},
		transitionTimingFunction: function(m) {
			this.indicatorStyle.webkitTransitionTimingFunction = m
		},
		refresh: function() {
			this.transitionTime();
			if(this.options.listenX && !this.options.listenY) {
				this.indicatorStyle.display = this.scroller.hasHorizontalScroll ? "block" : "none"
			} else {
				if(this.options.listenY && !this.options.listenX) {
					this.indicatorStyle.display = this.scroller.hasVerticalScroll ? "block" : "none"
				} else {
					this.indicatorStyle.display = this.scroller.hasHorizontalScroll || this.scroller.hasVerticalScroll ? "block" : "none"
				}
			}
			this.wrapper.offsetHeight;
			if(this.options.listenX) {
				this.wrapperWidth = this.wrapper.clientWidth;
				this.indicatorWidth = Math.max(Math.round(this.wrapperWidth * this.wrapperWidth / (this.scroller.scrollerWidth || this.wrapperWidth || 1)), 8);
				this.indicatorStyle.width = this.indicatorWidth + "px";
				this.maxPosX = this.wrapperWidth - this.indicatorWidth;
				this.minBoundaryX = 0;
				this.maxBoundaryX = this.maxPosX;
				this.sizeRatioX = this.options.speedRatioX || (this.scroller.maxScrollX && (this.maxPosX / this.scroller.maxScrollX))
			}
			if(this.options.listenY) {
				this.wrapperHeight = this.wrapper.clientHeight;
				this.indicatorHeight = Math.max(Math.round(this.wrapperHeight * this.wrapperHeight / (this.scroller.scrollerHeight || this.wrapperHeight || 1)), 8);
				this.indicatorStyle.height = this.indicatorHeight + "px";
				this.maxPosY = this.wrapperHeight - this.indicatorHeight;
				this.minBoundaryY = 0;
				this.maxBoundaryY = this.maxPosY;
				this.sizeRatioY = this.options.speedRatioY || (this.scroller.maxScrollY && (this.maxPosY / this.scroller.maxScrollY))
			}
			this.updatePosition()
		},
		updatePosition: function() {
			var m = this.options.listenX && Math.round(this.sizeRatioX * this.scroller.x) || 0,
				n = this.options.listenY && Math.round(this.sizeRatioY * this.scroller.y) || 0;
			if(m < this.minBoundaryX) {
				this.width = Math.max(this.indicatorWidth + m, 8);
				this.indicatorStyle.width = this.width + "px";
				m = this.minBoundaryX
			} else {
				if(m > this.maxBoundaryX) {
					this.width = Math.max(this.indicatorWidth - (m - this.maxPosX), 8);
					this.indicatorStyle.width = this.width + "px";
					m = this.maxPosX + this.indicatorWidth - this.width
				} else {
					if(this.width != this.indicatorWidth) {
						this.width = this.indicatorWidth;
						this.indicatorStyle.width = this.width + "px"
					}
				}
			}
			if(n < this.minBoundaryY) {
				this.height = Math.max(this.indicatorHeight + n * 3, 8);
				this.indicatorStyle.height = this.height + "px";
				n = this.minBoundaryY
			} else {
				if(n > this.maxBoundaryY) {
					this.height = Math.max(this.indicatorHeight - (n - this.maxPosY) * 3, 8);
					this.indicatorStyle.height = this.height + "px";
					n = this.maxPosY + this.indicatorHeight - this.height
				} else {
					if(this.height != this.indicatorHeight) {
						this.height = this.indicatorHeight;
						this.indicatorStyle.height = this.height + "px"
					}
				}
			}
			this.x = m;
			this.y = n;
			this.indicatorStyle.webkitTransform = this.scroller._getTranslateStr(m, n)
		},
		fade: function(p, o) {
			if(o && !this.visible) {
				return
			}
			clearTimeout(this.fadeTimeout);
			this.fadeTimeout = null;
			var n = p ? 250 : 500,
				m = p ? 0 : 300;
			p = p ? "1" : "0";
			this.wrapperStyle.webkitTransitionDuration = n + "ms";
			this.fadeTimeout = setTimeout((function(q) {
				this.wrapperStyle.opacity = q;
				this.visible = +q
			}).bind(this, p), m)
		}
	};
	e.Scroll = a;
	e.fn.scroll = function(m) {
		var n = [];
		this.each(function() {
			var q = null;
			var p = this;
			var r = p.getAttribute("data-scroll");
			if(!r) {
				r = ++e.uuid;
				var o = e.extend({}, m);
				if(p.classList.contains("mui-segmented-control")) {
					o = e.extend(o, {
						scrollY: false,
						scrollX: true,
						indicators: false,
						snap: ".mui-control-item"
					})
				}
				e.data[r] = q = new a(p, o);
				p.setAttribute("data-scroll", r)
			} else {
				q = e.data[r]
			}
			n.push(q)
		});
		return n.length === 1 ? n[0] : n
	}
})(mui, window, document);
(function(d, c, a, g) {
	var b = "mui-visibility";
	var f = "mui-hidden";
	var e = d.Scroll.extend(d.extend({
		handleEvent: function(h) {
			this._super(h);
			if(h.type === "scrollbottom") {
				this._scrollbottom()
			}
		},
		_scrollbottom: function() {
			if(!this.pulldown && !this.loading) {
				this.pulldown = false;
				this._initPullupRefresh();
				this.pullupLoading()
			}
		},
		_start: function(h) {
			if(!this.loading) {
				this.pulldown = this.pullPocket = this.pullCaption = this.pullLoading = false
			}
			this._super(h)
		},
		_drag: function(h) {
			this._super(h);
			if(!this.pulldown && !this.loading && this.topPocket && h.detail.direction === "down" && this.y >= 0) {
				this._initPulldownRefresh()
			}
			if(this.pulldown) {
				this._setCaption(this.y > this.options.down.height ? this.options.down.contentover : this.options.down.contentdown)
			}
		},
		_reLayout: function() {
			this.hasVerticalScroll = true;
			this._super()
		},
		resetPosition: function(h) {
			if(this.pulldown && this.y >= this.options.down.height) {
				this.pulldownLoading(0, h || 0);
				return true
			}
			return this._super(h)
		},
		pulldownLoading: function(h, i) {
			h = h || 0;
			this.scrollTo(h, this.options.down.height, i, this.options.bounceEasing);
			if(this.loading) {
				return
			}
			if(!this.pulldown) {
				this._initPulldownRefresh()
			}
			this._setCaption(this.options.down.contentrefresh);
			this.loading = true;
			this.indicators.map(function(k) {
				k.fade(0)
			});
			var j = this.options.down.callback;
			j && j.call(this)
		},
		endPulldownToRefresh: function() {
			var h = this;
			if(h.topPocket && h.loading && this.pulldown) {
				h.scrollTo(0, 0, h.options.bounceTime, h.options.bounceEasing);
				h.loading = false;
				h._setCaption(h.options.down.contentdown, true);
				setTimeout(function() {
					h.loading || h.topPocket.classList.remove(b)
				}, 350)
			}
		},
		pullupLoading: function(j, h, i) {
			h = h || 0;
			this.scrollTo(h, this.maxScrollY, i, this.options.bounceEasing);
			if(this.loading) {
				return
			}
			this._initPullupRefresh();
			this._setCaption(this.options.up.contentrefresh);
			this.indicators.map(function(k) {
				k.fade(0)
			});
			this.loading = true;
			j = j || this.options.up.callback;
			j && j.call(this)
		},
		endPullupToRefresh: function(i) {
			var h = this;
			if(h.bottomPocket && h.loading && !this.pulldown) {
				h.loading = false;
				if(i) {
					h._setCaption(h.options.up.contentnomore);
					h.wrapper.removeEventListener("scrollbottom", h)
				} else {
					h._setCaption(h.options.up.contentdown);
					setTimeout(function() {
						h.loading || h.bottomPocket.classList.remove(b)
					}, 350)
				}
			}
		},
		refresh: function(h) {
			if(h) {
				this.wrapper.addEventListener("scrollbottom", this)
			}
			this._super()
		},
	}, d.PullRefresh));
	d.fn.pullRefresh = function(j) {
		if(this.length === 1) {
			var h = this[0];
			var i = null;
			var k = h.getAttribute("data-pullrefresh");
			if(!k) {
				k = ++d.uuid;
				d.data[k] = i = new e(h, j);
				h.setAttribute("data-pullrefresh", k)
			} else {
				i = d.data[k]
			}
			return i
		}
	}
})(mui, window, document);
(function(f, h) {
	var i = "mui-slider";
	var a = "mui-slider-group";
	var k = "mui-slider-loop";
	var n = "mui-slider-indicator";
	var j = "mui-action-previous";
	var m = "mui-action-next";
	var b = "mui-slider-item";
	var l = "mui-active";
	var d = "." + b;
	var g = "." + n;
	var c = ".mui-slider-progress-bar";
	var e = f.Scroll.extend({
		init: function(p, o) {
			this._super(p, f.extend(true, {
				interval: 0,
				scrollY: false,
				scrollX: true,
				indicators: false,
				bounceTime: 200,
				startX: false,
				snap: d
			}, o));
			if(this.options.startX) {}
		},
		_init: function() {
			var p = this.wrapper.querySelectorAll("." + a);
			for(var q = 0, o = p.length; q < o; q++) {
				if(p[q].parentNode === this.wrapper) {
					this.scroller = p[q];
					break
				}
			}
			if(this.scroller) {
				this.scrollerStyle = this.scroller.style;
				this.progressBar = this.wrapper.querySelector(c);
				if(this.progressBar) {
					this.progressBarWidth = this.progressBar.offsetWidth;
					this.progressBarStyle = this.progressBar.style
				}
				this._super();
				this._initTimer()
			}
		},
		_initEvent: function() {
			var p = this;
			p._super();
			p.wrapper.addEventListener("swiperight", f.stopPropagation);
			p.wrapper.addEventListener("scrollend", function() {
				p.isInTransition = false;
				var r = p.currentPage;
				var q = p.slideNumber;
				p.slideNumber = p._fixedSlideNumber();
				if(p.loop) {
					if(p.slideNumber === 0) {
						p.setTranslate(p.pages[1][0].x, 0)
					} else {
						if(p.slideNumber === p.itemLength - 3) {
							p.setTranslate(p.pages[p.itemLength - 2][0].x, 0)
						}
					}
				}
				if(q != p.slideNumber) {
					f.trigger(p.wrapper, "slide", {
						slideNumber: p.slideNumber
					})
				}
			});
			p.wrapper.addEventListener("slide", function(w) {
				if(w.target !== p.wrapper) {
					return
				}
				var u = w.detail;
				u.slideNumber = u.slideNumber || 0;
				var x = p.scroller.querySelectorAll(d);
				var q = u.slideNumber;
				if(p.loop) {
					q += 1
				}
				if(!p.wrapper.classList.contains("mui-segmented-control")) {
					for(var t = 0, v = x.length; t < v; t++) {
						var A = x[t];
						if(A.parentNode === p.scroller) {
							if(t === q) {
								A.classList.add(l)
							} else {
								A.classList.remove(l)
							}
						}
					}
				}
				var y = p.wrapper.querySelector(".mui-slider-indicator");
				if(y) {
					if(y.getAttribute("data-scroll")) {
						f(y).scroll().gotoPage(u.slideNumber)
					}
					var z = y.querySelectorAll(".mui-indicator");
					if(z.length > 0) {
						for(var t = 0, v = z.length; t < v; t++) {
							z[t].classList[t === u.slideNumber ? "add" : "remove"](l)
						}
					} else {
						var s = y.querySelector(".mui-number span");
						if(s) {
							s.innerText = (u.slideNumber + 1)
						} else {
							var r = p.wrapper.querySelectorAll(".mui-control-item");
							for(var t = 0, v = r.length; t < v; t++) {
								r[t].classList[t === u.slideNumber ? "add" : "remove"](l)
							}
						}
					}
				}
				w.stopPropagation()
			});
			p.wrapper.addEventListener(f.eventName("shown", "tab"), function(q) {
				p.gotoItem((q.detail.tabNumber || 0), p.options.bounceTime)
			});
			var o = p.wrapper.querySelector(g);
			if(o) {
				o.addEventListener("tap", function(q) {
					var r = q.target;
					if(r.classList.contains(j) || r.classList.contains(m)) {
						p[r.classList.contains(j) ? "prevItem" : "nextItem"]();
						q.stopPropagation()
					}
				})
			}
		},
		_drag: function(p) {
			this._super(p);
			var o = p.detail.direction;
			if(o === "left" || o === "right") {
				p.stopPropagation()
			}
		},
		_initTimer: function() {
			var q = this;
			var r = q.wrapper;
			var p = q.options.interval;
			var o = r.getAttribute("data-slidershowTimer");
			o && h.clearTimeout(o);
			if(p) {
				o = h.setTimeout(function() {
					if(!r) {
						return
					}
					if(!!(r.offsetWidth || r.offsetHeight)) {
						q.nextItem(true)
					}
					q._initTimer()
				}, p);
				r.setAttribute("data-slidershowTimer", o)
			}
		},
		_fixedSlideNumber: function(p) {
			p = p || this.currentPage;
			var o = p.pageX;
			if(this.loop) {
				if(p.pageX === 0) {
					o = this.itemLength - 3
				} else {
					if(p.pageX === (this.itemLength - 1)) {
						o = 0
					} else {
						o = p.pageX - 1
					}
				}
			}
			return o
		},
		_reLayout: function() {
			this.hasHorizontalScroll = true;
			this.loop = this.scroller.classList.contains(k);
			this._super()
		},
		_getScroll: function() {
			var o = f.parseTranslateMatrix(f.getStyles(this.scroller, "webkitTransform"));
			return o ? o.x : 0
		},
		_transitionEnd: function(o) {
			if(o.target !== this.scroller || !this.isInTransition) {
				return
			}
			this._transitionTime();
			this.isInTransition = false;
			f.trigger(this.wrapper, "scrollend", this)
		},
		_flick: function(q) {
			if(!this.moved) {
				return
			}
			var o = q.detail;
			var p = o.direction;
			this._clearRequestAnimationFrame();
			this.isInTransition = true;
			if(q.type === "flick") {
				if(o.touchTime < 200) {
					this.x = this._getPage((this.slideNumber + (p === "right" ? -1 : 1)), true).x
				}
				this.resetPosition(this.options.bounceTime)
			} else {
				if(q.type === "dragend" && !o.flick) {
					this.resetPosition(this.options.bounceTime)
				}
			}
			q.stopPropagation()
		},
		_initSnap: function() {
			this.scrollerWidth = this.itemLength * this.scrollerWidth;
			this.maxScrollX = Math.min(this.wrapperWidth - this.scrollerWidth, 0);
			this._super();
			if(!this.currentPage.x) {
				var o = this.pages[this.loop ? 1 : 0];
				o = o || this.pages[0];
				if(!o) {
					return
				}
				this.currentPage = o[0];
				this.slideNumber = 0
			} else {
				this.slideNumber = this._fixedSlideNumber()
			}
			this.options.startX = this.currentPage.x || 0
		},
		_getSnapX: function(o) {
			return Math.max(-o, this.maxScrollX)
		},
		_getPage: function(o, p) {
			if(this.loop) {
				if(o > (this.itemLength - (p ? 2 : 3))) {
					o = 1;
					time = 0
				} else {
					if(o < (p ? -1 : 0)) {
						o = this.itemLength - 2;
						time = 0
					} else {
						o += 1
					}
				}
			} else {
				if(!p) {
					if(o > (this.itemLength - 1)) {
						o = 0;
						time = 0
					} else {
						if(o < 0) {
							o = this.itemLength - 1;
							time = 0
						}
					}
				}
				o = Math.min(Math.max(0, o), this.itemLength - 1)
			}
			return this.pages[o][0]
		},
		_gotoItem: function(o, p) {
			this.currentPage = this._getPage(o, true);
			this.scrollTo(this.currentPage.x, 0, p, this.options.bounceEasing);
			if(p === 0) {
				f.trigger(this.wrapper, "scrollend", this)
			}
			this._initTimer()
		},
		setTranslate: function(o, q) {
			this._super(o, q);
			var p = this.progressBar;
			if(p) {
				this.progressBarStyle.webkitTransform = this._getTranslateStr((-o * (this.progressBarWidth / this.wrapperWidth)), 0)
			}
		},
		resetPosition: function(o) {
			o = o || 0;
			if(this.x > 0) {
				this.x = 0
			} else {
				if(this.x < this.maxScrollX) {
					this.x = this.maxScrollX
				}
			}
			this.currentPage = this._nearestSnap(this.x);
			this.scrollTo(this.currentPage.x, 0, o);
			return true
		},
		gotoItem: function(o, p) {
			this._gotoItem(o, p || this.options.bounceTime)
		},
		nextItem: function() {
			this._gotoItem(this.slideNumber + 1, this.options.bounceTime)
		},
		prevItem: function() {
			this._gotoItem(this.slideNumber - 1, this.options.bounceTime)
		},
		getSlideNumber: function() {
			return this.slideNumber || 0
		},
		refresh: function(o) {
			if(o) {
				f.extend(this.options, o);
				this._super();
				this.nextItem()
			} else {
				this._super()
			}
		}
	});
	f.fn.slider = function(o) {
		var p = null;
		this.each(function() {
			var r = this;
			if(!this.classList.contains(i)) {
				r = this.querySelector("." + i)
			}
			if(r && r.querySelector(d)) {
				var q = r.getAttribute("data-slider");
				if(!q) {
					q = ++f.uuid;
					f.data[q] = p = new e(r, o);
					r.setAttribute("data-slider", q)
				} else {
					p = f.data[q];
					if(p && o) {
						p.refresh(o)
					}
				}
			}
		});
		return p
	};
	f.ready(function() {
		f(".mui-slider").slider();
		f(".mui-scroll-wrapper.mui-slider-indicator.mui-segmented-control").scroll({
			scrollY: false,
			scrollX: true,
			indicators: false,
			snap: ".mui-control-item"
		})
	})
})(mui, window);
(function(d, a) {
	if(!(d.os.plus && d.os.android)) {
		return
	}
	var f = "mui-plus-pullrefresh";
	var b = "mui-visibility";
	var g = "mui-hidden";
	var e = "mui-block";
	var c = d.Class.extend({
		init: function(i, h) {
			this.element = i;
			this.options = h;
			this.wrapper = this.scroller = i;
			this._init();
			this._initPulldownRefreshEvent()
		},
		_init: function() {
			var h = this;
			window.addEventListener("dragup", h);
			h.scrollInterval = window.setInterval(function() {
				if(h.isScroll && !h.loading) {
					if(window.pageYOffset + window.innerHeight + 10 >= a.documentElement.scrollHeight) {
						h.isScroll = false;
						if(h.bottomPocket) {
							h.pullupLoading()
						}
					}
				}
			}, 100)
		},
		_initPulldownRefreshEvent: function() {
			var h = this;
			if(h.topPocket && h.options.webviewId) {
				d.plusReady(function() {
					var j = plus.webview.getWebviewById(h.options.webviewId);
					if(!j) {
						return
					}
					h.options.webview = j;
					var k = h.options.down;
					var i = k.height;
					j.addEventListener("dragBounce", function(l) {
						if(!h.pulldown) {
							h._initPulldownRefresh()
						} else {
							h.pullPocket.classList.add(e)
						}
						switch(l.status) {
							case "beforeChangeOffset":
								h._setCaption(k.contentdown);
								break;
							case "afterChangeOffset":
								h._setCaption(k.contentover);
								break;
							case "dragEndAfterChangeOffset":
								j.evalJS("mui&&mui.options.pullRefresh.down.callback()");
								h._setCaption(k.contentrefresh);
								break;
							default:
								break
						}
					}, false);
					j.setBounce({
						position: {
							top: i * 2 + "px"
						},
						changeoffset: {
							top: i + "px"
						}
					})
				})
			}
		},
		handleEvent: function(i) {
			var h = this;
			if(h.stopped) {
				return
			}
			h.isScroll = false;
			if(i.type === "dragup") {
				h.isScroll = true;
				setTimeout(function() {
					h.isScroll = false
				}, 1000)
			}
		}
	}).extend(d.extend({
		setStopped: function(j) {
			this.stopped = !!j;
			var i = plus.webview.currentWebview();
			if(this.stopped) {
				i.setStyle({
					bounce: "none"
				});
				i.setBounce({
					position: {
						top: "none"
					}
				})
			} else {
				var h = this.options.down.height;
				i.setStyle({
					bounce: "vertical"
				});
				i.setBounce({
					position: {
						top: h * 2 + "px"
					},
					changeoffset: {
						top: h + "px"
					}
				})
			}
		},
		pulldownLoading: function() {
			throw new Error("暂不支持")
		},
		endPulldownToRefresh: function() {
			var h = plus.webview.currentWebview();
			h.parent().evalJS("mui&&mui(document.querySelector('.mui-content')).pullRefresh('" + JSON.stringify({
				webviewId: h.id
			}) + "')._endPulldownToRefresh()")
		},
		_endPulldownToRefresh: function() {
			var h = this;
			if(h.topPocket && h.options.webview) {
				h.options.webview.endPullToRefresh();
				h.loading = false;
				h._setCaption(h.options.down.contentdown, true);
				setTimeout(function() {
					h.loading || h.topPocket.classList.remove(e)
				}, 350)
			}
		},
		pullupLoading: function(i) {
			var h = this;
			if(h.isLoading) {
				return
			}
			h.isLoading = true;
			if(h.pulldown !== false) {
				h._initPullupRefresh()
			} else {
				this.pullPocket.classList.add(e)
			}
			setTimeout(function() {
				h.pullLoading.classList.add(b);
				h.pullLoading.classList.remove(g);
				h.pullCaption.innerHTML = "";
				h.pullCaption.innerHTML = h.options.up.contentrefresh;
				i = i || h.options.up.callback;
				i && i.call(h)
			}, 300)
		},
		endPullupToRefresh: function(i) {
			var h = this;
			if(h.pullLoading) {
				h.pullLoading.classList.remove(b);
				h.pullLoading.classList.add(g);
				h.isLoading = false;
				if(i) {
					h.pullCaption.innerHTML = h.options.up.contentnomore;
					window.removeEventListener("dragup", h)
				} else {
					h.pullCaption.innerHTML = h.options.up.contentdown
				}
			}
		},
		scrollTo: function(h, j, i) {
			d.scrollTo(h, j, i)
		},
		refresh: function(h) {
			if(h) {
				window.addEventListener("dragup", this)
			}
		}
	}, d.PullRefresh));
	d.fn.pullRefresh = function(i) {
		var h;
		if(this.length === 0) {
			h = a.createElement("div");
			h.className = "mui-content";
			a.body.appendChild(h)
		} else {
			h = this[0]
		}
		i = i || {
			webviewId: plus.webview.currentWebview().id || plus.webview.currentWebview().getURL()
		};
		if(typeof i === "string") {
			i = d.parseJSON(i)
		}
		var l = null;
		var j = i.webviewId && i.webviewId.replace(/\//g, "_");
		var k = h.getAttribute("data-pullrefresh-plus-" + j);
		if(!k) {
			k = ++d.uuid;
			h.setAttribute("data-pullrefresh-plus-" + j, k);
			a.body.classList.add(f);
			d.data[k] = l = new c(h, i)
		} else {
			l = d.data[k]
		}
		return l
	}
})(mui, document);
(function(d, k, l, b) {
	var c = "mui-off-canvas-left";
	var n = "mui-off-canvas-right";
	var h = "mui-off-canvas-backdrop";
	var o = "mui-off-canvas-wrap";
	var j = "mui-slide-in";
	var m = "mui-active";
	var a = "mui-transitioning";
	var i = ".mui-inner-wrap";
	var f = d.Class.extend({
		init: function(q, p) {
			this.wrapper = this.element = q;
			this.scroller = this.wrapper.querySelector(i);
			this.classList = this.wrapper.classList;
			if(this.scroller) {
				this.options = d.extend(true, {
					dragThresholdX: 10
				}, p);
				l.body.classList.add("mui-fullscreen");
				this.refresh();
				this.initEvent()
			}
		},
		refresh: function(p) {
			this.slideIn = this.classList.contains(j);
			this.scroller = this.wrapper.querySelector(i);
			this.offCanvasLefts = this.wrapper.querySelectorAll("." + c);
			this.offCanvasRights = this.wrapper.querySelectorAll("." + n);
			if(p) {
				if(p.classList.contains(c)) {
					this.offCanvasLeft = p
				} else {
					if(p.classList.contains(n)) {
						this.offCanvasRight = p
					}
				}
			} else {
				this.offCanvasRight = this.wrapper.querySelector("." + n);
				this.offCanvasLeft = this.wrapper.querySelector("." + c)
			}
			this.offCanvasRightWidth = this.offCanvasLeftWidth = 0;
			this.offCanvasLeftSlideIn = this.offCanvasRightSlideIn = false;
			if(this.offCanvasRight) {
				this.offCanvasRightWidth = this.offCanvasRight.offsetWidth;
				this.offCanvasRightSlideIn = this.slideIn && (this.offCanvasRight.parentNode === this.wrapper)
			}
			if(this.offCanvasLeft) {
				this.offCanvasLeftWidth = this.offCanvasLeft.offsetWidth;
				this.offCanvasLeftSlideIn = this.slideIn && (this.offCanvasLeft.parentNode === this.wrapper)
			}
			this.backdrop = this.scroller.querySelector("." + h);
			this.options.dragThresholdX = this.options.dragThresholdX || 10;
			this.visible = false;
			this.startX = null;
			this.lastX = null;
			this.offsetX = null;
			this.lastTranslateX = null
		},
		handleEvent: function(u) {
			switch(u.type) {
				case "touchstart":
					var q = u.target && u.target.tagName;
					if(q !== "INPUT" && q !== "TEXTAREA" && q !== "SELECT") {
						u.preventDefault()
					}
					break;
				case "webkitTransitionEnd":
					if(u.target === this.scroller) {
						this._dispatchEvent()
					}
					break;
				case "drag":
					var s = u.detail;
					if(!this.startX) {
						this.startX = s.move.x;
						this.lastX = this.startX
					} else {
						this.lastX = s.move.x
					}
					if(!this.isDragging && Math.abs(this.lastX - this.startX) > this.options.dragThresholdX && (s.direction === "left" || (s.direction === "right"))) {
						if(this.slideIn) {
							if(this.classList.contains(m)) {
								this.scroller = this.offCanvasRight && this.offCanvasRight.classList.contains(m) ? this.offCanvasRight : this.offCanvasLeft
							} else {
								if(s.direction === "left" && this.offCanvasRight) {
									this.scroller = this.offCanvasRight
								} else {
									if(s.direction === "right" && this.offCanvasLeft) {
										this.scroller = this.offCanvasLeft
									} else {
										this.scroller = null
									}
								}
							}
						}
						if(this.scroller) {
							this.startX = this.lastX;
							this.isDragging = true;
							d.gestures.touch.lockDirection = true;
							d.gestures.touch.startDirection = s.direction;
							this.scroller.classList.remove(a);
							this.offsetX = this.getTranslateX();
							this._initOffCanvasVisible()
						}
					}
					if(this.isDragging) {
						this.updateTranslate(this.offsetX + (this.lastX - this.startX));
						s.gesture.preventDefault();
						u.stopPropagation()
					}
					break;
				case "dragend":
					if(this.isDragging) {
						var s = u.detail;
						var t = s.direction;
						this.isDragging = false;
						this.scroller.classList.add(a);
						var r = 0;
						var p = this.getTranslateX();
						if(!this.slideIn) {
							if(p >= 0) {
								r = (this.offCanvasLeftWidth && (p / this.offCanvasLeftWidth)) || 0
							} else {
								r = (this.offCanvasRightWidth && (p / this.offCanvasRightWidth)) || 0
							}
							if(r === 0) {
								this.openPercentage(0);
								this._dispatchEvent();
								return
							}
							if(r > 0 && r < 0.5 && t === "right") {
								this.openPercentage(0)
							} else {
								if(r > 0.5 && t === "left") {
									this.openPercentage(100)
								} else {
									if(r < 0 && r > -0.5 && t === "left") {
										this.openPercentage(0)
									} else {
										if(t === "right" && r < 0 && r > -0.5) {
											this.openPercentage(0)
										} else {
											if(r < 0.5 && t === "right") {
												this.openPercentage(-100)
											} else {
												if(t === "right" && r >= 0 && (r >= 0.5 || s.flick)) {
													this.openPercentage(100)
												} else {
													if(t === "left" && r <= 0 && (r <= -0.5 || s.flick)) {
														this.openPercentage(-100)
													} else {
														this.openPercentage(0)
													}
												}
											}
										}
									}
								}
							}
							if(r === 1 || r === -1) {
								this._dispatchEvent()
							}
						} else {
							if(p >= 0) {
								r = (this.offCanvasRightWidth && (p / this.offCanvasRightWidth)) || 0
							} else {
								r = (this.offCanvasLeftWidth && (p / this.offCanvasLeftWidth)) || 0
							}
							if(r >= 0.5 && t === "left") {
								this.openPercentage(0)
							} else {
								if(r > 0 && r <= 0.5 && t === "left") {
									this.openPercentage(-100)
								} else {
									if(r >= 0.5 && t === "right") {
										this.openPercentage(0)
									} else {
										if(r >= -0.5 && r < 0 && t === "left") {
											this.openPercentage(100)
										} else {
											if(r > 0 && r <= 0.5 && t === "right") {
												this.openPercentage(-100)
											} else {
												if(r <= -0.5 && t === "right") {
													this.openPercentage(0)
												} else {
													if(r >= -0.5 && t === "right") {
														this.openPercentage(100)
													} else {
														if(r <= -0.5 && t === "left") {
															this.openPercentage(0)
														} else {
															if(r >= -0.5 && t === "left") {
																this.openPercentage(-100)
															} else {
																this.openPercentage(0)
															}
														}
													}
												}
											}
										}
									}
								}
							}
							if(r === 1 || r === -1 || r === 0) {
								this._dispatchEvent();
								return
							}
						}
					}
					break
			}
		},
		_dispatchEvent: function() {
			if(this.classList.contains(m)) {
				d.trigger(this.wrapper, "shown", this)
			} else {
				d.trigger(this.wrapper, "hidden", this)
			}
		},
		_initOffCanvasVisible: function() {
			if(!this.visible) {
				this.visible = true;
				if(this.offCanvasLeft) {
					this.offCanvasLeft.style.visibility = "visible"
				}
				if(this.offCanvasRight) {
					this.offCanvasRight.style.visibility = "visible"
				}
			}
		},
		initEvent: function() {
			var p = this;
			if(p.backdrop) {
				p.backdrop.addEventListener("tap", function(q) {
					p.close();
					q.detail.gesture.preventDefault()
				})
			}
			if(this.classList.contains("mui-draggable")) {
				this.wrapper.addEventListener("touchstart", this);
				this.wrapper.addEventListener("drag", this);
				this.wrapper.addEventListener("dragend", this)
			}
			this.wrapper.addEventListener("webkitTransitionEnd", this)
		},
		openPercentage: function(q) {
			var r = q / 100;
			if(!this.slideIn) {
				if(this.offCanvasLeft && q >= 0) {
					this.updateTranslate(this.offCanvasLeftWidth * r);
					this.offCanvasLeft.classList[r !== 0 ? "add" : "remove"](m)
				} else {
					if(this.offCanvasRight && q <= 0) {
						this.updateTranslate(this.offCanvasRightWidth * r);
						this.offCanvasRight.classList[r !== 0 ? "add" : "remove"](m)
					}
				}
				this.classList[r !== 0 ? "add" : "remove"](m)
			} else {
				if(this.offCanvasLeft && q >= 0) {
					r = r === 0 ? -1 : 0;
					this.updateTranslate(this.offCanvasLeftWidth * r);
					this.offCanvasLeft.classList[q !== 0 ? "add" : "remove"](m)
				} else {
					if(this.offCanvasRight && q <= 0) {
						r = r === 0 ? 1 : 0;
						this.updateTranslate(this.offCanvasRightWidth * r);
						this.offCanvasRight.classList[q !== 0 ? "add" : "remove"](m)
					}
				}
				this.classList[q !== 0 ? "add" : "remove"](m)
			}
		},
		updateTranslate: function(p) {
			if(p !== this.lastTranslateX) {
				if(!this.slideIn) {
					if((!this.offCanvasLeft && p > 0) || (!this.offCanvasRight && p < 0)) {
						this.setTranslateX(0);
						return
					}
					if(this.leftShowing && p > this.offCanvasLeftWidth) {
						this.setTranslateX(this.offCanvasLeftWidth);
						return
					}
					if(this.rightShowing && p < -this.offCanvasRightWidth) {
						this.setTranslateX(-this.offCanvasRightWidth);
						return
					}
					this.setTranslateX(p);
					if(p >= 0) {
						this.leftShowing = true;
						this.rightShowing = false;
						if(p > 0) {
							if(this.offCanvasLeft) {
								d.each(this.offCanvasLefts, function(q, r) {
									if(r === this.offCanvasLeft) {
										this.offCanvasLeft.style.zIndex = 0
									} else {
										r.style.zIndex = -1
									}
								}.bind(this))
							}
							if(this.offCanvasRight) {
								this.offCanvasRight.style.zIndex = -1
							}
						}
					} else {
						this.rightShowing = true;
						this.leftShowing = false;
						if(this.offCanvasRight) {
							d.each(this.offCanvasRights, function(q, r) {
								console.log(r === this.offCanvasRight);
								if(r === this.offCanvasRight) {
									r.style.zIndex = 0
								} else {
									r.style.zIndex = -1
								}
							}.bind(this))
						}
						if(this.offCanvasLeft) {
							this.offCanvasLeft.style.zIndex = -1
						}
					}
				} else {
					if(this.scroller.classList.contains(n)) {
						if(p < 0) {
							this.setTranslateX(0);
							return
						}
						if(p > this.offCanvasRightWidth) {
							this.setTranslateX(this.offCanvasRightWidth);
							return
						}
					} else {
						if(p > 0) {
							this.setTranslateX(0);
							return
						}
						if(p < -this.offCanvasLeftWidth) {
							this.setTranslateX(-this.offCanvasLeftWidth);
							return
						}
					}
					this.setTranslateX(p)
				}
				this.lastTranslateX = p
			}
		},
		setTranslateX: d.animationFrame(function(p) {
			if(this.scroller) {
				this.scroller.style.webkitTransform = "translate3d(" + p + "px,0,0)"
			}
		}),
		getTranslateX: function() {
			if(this.scroller) {
				var p = d.parseTranslateMatrix(d.getStyles(this.scroller, "webkitTransform"));
				return(p && p.x) || 0
			}
			return 0
		},
		isShown: function(r) {
			var q = false;
			if(!this.slideIn) {
				var p = this.getTranslateX();
				if(r === "right") {
					q = this.classList.contains(m) && p < 0
				} else {
					if(r === "left") {
						q = this.classList.contains(m) && p > 0
					} else {
						q = this.classList.contains(m) && p !== 0
					}
				}
			} else {
				if(r === "left") {
					q = this.classList.contains(m) && this.wrapper.querySelector("." + c + "." + m)
				} else {
					if(r === "right") {
						q = this.classList.contains(m) && this.wrapper.querySelector("." + n + "." + m)
					} else {
						q = this.classList.contains(m) && (this.wrapper.querySelector("." + c + "." + m) || this.wrapper.querySelector("." + n + "." + m))
					}
				}
			}
			return q
		},
		close: function() {
			this._initOffCanvasVisible();
			if(this.slideIn) {
				this.scroller = this.wrapper.querySelector("." + n + "." + m) || this.wrapper.querySelector("." + c + "." + m)
			}
			if(this.scroller) {
				this.scroller.classList.add(a);
				this.openPercentage(0)
			}
		},
		show: function(p) {
			this._initOffCanvasVisible();
			if(this.isShown(p)) {
				return false
			}
			if(!p) {
				p = this.wrapper.querySelector("." + n) ? "right" : "left"
			}
			if(this.slideIn) {
				this.scroller = p === "right" ? this.offCanvasRight : this.offCanvasLeft
			}
			if(this.scroller) {
				this.scroller.classList.add(a);
				this.openPercentage(p === "left" ? 100 : -100)
			}
			return true
		},
		toggle: function(q) {
			var p = q;
			if(q && q.classList) {
				p = q.classList.contains(c) ? "left" : "right";
				this.refresh(q)
			}
			if(!this.show(p)) {
				this.close()
			}
		}
	});
	var e = function(p) {
		parentNode = p.parentNode;
		if(parentNode) {
			if(parentNode.classList.contains(o)) {
				return parentNode
			} else {
				parentNode = parentNode.parentNode;
				if(parentNode.classList.contains(o)) {
					return parentNode
				}
			}
		}
	};
	var g = function(r, s) {
		if(s.tagName === "A" && s.hash) {
			var p = l.getElementById(s.hash.replace("#", ""));
			if(p) {
				var q = e(p);
				if(q) {
					d.targets._container = q;
					return p
				}
			}
		}
		return false
	};
	d.registerTarget({
		name: b,
		index: 60,
		handle: g,
		target: false,
		isReset: false,
		isContinue: true
	});
	k.addEventListener("tap", function(q) {
		if(!d.targets.offcanvas) {
			return
		}
		var p = q.target;
		for(; p && p !== l; p = p.parentNode) {
			if(p.tagName === "A" && p.hash && p.hash === ("#" + d.targets.offcanvas.id)) {
				q.detail.gesture.preventDefault();
				d(d.targets._container).offCanvas().toggle(d.targets.offcanvas);
				d.targets.offcanvas = d.targets._container = null;
				break
			}
		}
	});
	d.fn.offCanvas = function(p) {
		var q = [];
		this.each(function() {
			var t = null;
			var r = this;
			if(!r.classList.contains(o)) {
				r = e(r)
			}
			var s = r.getAttribute("data-offCanvas");
			if(!s) {
				s = ++d.uuid;
				d.data[s] = t = new f(r, p);
				r.setAttribute("data-offCanvas", s)
			} else {
				t = d.data[s]
			}
			if(p === "show" || p === "close" || p === "toggle") {
				t.toggle()
			}
			q.push(t)
		});
		return q.length === 1 ? q[0] : q
	};
	d.ready(function() {
		d(".mui-off-canvas-wrap").offCanvas()
	})
})(mui, window, document, "offcanvas");
(function(c, a) {
	var d = "mui-action";
	var b = function(e, f) {
		if(f.className && ~f.className.indexOf(d)) {
			e.preventDefault();
			return f
		}
		return false
	};
	c.registerTarget({
		name: a,
		index: 50,
		handle: b,
		target: false,
		isContinue: true
	})
})(mui, "action");
(function(f, d, a, b) {
	var c = "mui-modal";
	var e = function(h, i) {
		if(i.tagName === "A" && i.hash) {
			var g = a.getElementById(i.hash.replace("#", ""));
			if(g && g.classList.contains(c)) {
				return g
			}
		}
		return false
	};
	f.registerTarget({
		name: b,
		index: 50,
		handle: e,
		target: false,
		isReset: false,
		isContinue: true
	});
	d.addEventListener("tap", function(g) {
		if(f.targets.modal) {
			g.detail.gesture.preventDefault();
			f.targets.modal.classList.toggle("mui-active")
		}
	})
})(mui, window, document, "modal");
(function(e, l, o, v) {
	var n = "mui-popover";
	var s = "mui-popover-arrow";
	var f = "mui-popover-action";
	var a = "mui-backdrop";
	var t = "mui-bar-popover";
	var j = "mui-bar-backdrop";
	var u = "mui-backdrop-action";
	var d = "mui-active";
	var h = "mui-bottom";
	var r = function(w, x) {
		if(x.tagName === "A" && x.hash) {
			e.targets._popover = o.getElementById(x.hash.replace("#", ""));
			if(e.targets._popover && e.targets._popover.classList.contains(n)) {
				return x
			} else {
				e.targets._popover = null
			}
		}
		return false
	};
	e.registerTarget({
		name: v,
		index: 60,
		handle: r,
		target: false,
		isReset: false,
		isContinue: true
	});
	var m = function(w) {};
	var g = function(w) {
		this.removeEventListener("webkitTransitionEnd", g);
		this.addEventListener("touchmove", e.preventDefault);
		e.trigger(this, "shown", this)
	};
	var k = function(w) {
		c(this, "none");
		this.removeEventListener("webkitTransitionEnd", k);
		this.removeEventListener("touchmove", e.preventDefault);
		m(false);
		e.trigger(this, "hidden", this)
	};
	var p = (function() {
		var w = o.createElement("div");
		w.classList.add(a);
		w.addEventListener("touchmove", e.preventDefault);
		w.addEventListener("tap", function(y) {
			var x = e.targets._popover;
			if(x) {
				x.addEventListener("webkitTransitionEnd", k);
				x.classList.remove(d);
				b(x);
				o.body.setAttribute("style", "")
			}
		});
		return w
	}());
	var b = function(w) {
		p.setAttribute("style", "opacity:0");
		e.targets.popover = e.targets._popover = null;
		setTimeout(function() {
			if(!w.classList.contains(d) && p.parentNode && p.parentNode === o.body) {
				o.body.removeChild(p)
			}
		}, 350)
	};
	l.addEventListener("tap", function(y) {
		if(!e.targets.popover) {
			return
		}
		var w = false;
		var x = y.target;
		for(; x && x !== o; x = x.parentNode) {
			if(x === e.targets.popover) {
				w = true
			}
		}
		if(w) {
			y.detail.gesture.preventDefault();
			i(e.targets._popover, e.targets.popover)
		}
	});
	var i = function(z, x) {
		z.removeEventListener("webkitTransitionEnd", g);
		z.removeEventListener("webkitTransitionEnd", k);
		p.classList.remove(j);
		p.classList.remove(u);
		var y = o.querySelector(".mui-popover.mui-active");
		if(y) {
			y.addEventListener("webkitTransitionEnd", k);
			y.classList.remove(d);
			if(z === y) {
				b(y);
				return
			}
		}
		var w = false;
		if(z.classList.contains(t) || z.classList.contains(f)) {
			if(z.classList.contains(f)) {
				w = true;
				p.classList.add(u)
			} else {
				p.classList.add(j)
			}
		}
		c(z, "block");
		z.offsetHeight;
		z.classList.add(d);
		p.setAttribute("style", "");
		o.body.appendChild(p);
		m(true);
		q(z, x, w);
		p.classList.add(d);
		z.addEventListener("webkitTransitionEnd", g)
	};
	var c = function(x, A, z, y) {
		var w = x.style;
		if(typeof A !== "undefined") {
			w.display = A
		}
		if(typeof z !== "undefined") {
			w.top = z + "px"
		}
		if(typeof y !== "undefined") {
			w.left = y + "px"
		}
	};
	var q = function(A, E, L) {
		if(!A || !E) {
			return
		}
		var I = l.innerWidth;
		var D = l.innerHeight;
		var K = A.offsetWidth;
		var C = A.offsetHeight;
		if(L) {
			c(A, "block", (D - C + l.pageYOffset), (I - K) / 2);
			return
		}
		var M = E.offsetWidth;
		var z = E.offsetHeight;
		var B = e.offset(E);
		var w = A.querySelector("." + s);
		if(!w) {
			w = o.createElement("div");
			w.className = s;
			A.appendChild(w)
		}
		var J = w && w.offsetWidth / 2 || 0;
		var H = 0;
		var G = 0;
		var F = 0;
		var x = 0;
		var y = A.classList.contains(f) ? 0 : 5;
		var N = "top";
		if((C + J) < (B.top - l.pageYOffset)) {
			H = B.top - C - J
		} else {
			if((C + J) < (D - (B.top - l.pageYOffset) - z)) {
				N = "bottom";
				H = B.top + z + J
			} else {
				N = "middle";
				H = Math.max((D - C) / 2 + l.pageYOffset, 0);
				G = Math.max((I - K) / 2 + l.pageXOffset, 0)
			}
		}
		if(N === "top" || N === "bottom") {
			G = M / 2 + B.left - K / 2;
			F = G;
			if(G < y) {
				G = y
			}
			if(G + K > I) {
				G = I - K - y
			}
			if(w) {
				if(N === "top") {
					w.classList.add(h)
				} else {
					w.classList.remove(h)
				}
				F = F - G;
				x = (K / 2 - J / 2 + F);
				x = Math.max(Math.min(x, K - J * 2 - 6), 6);
				w.setAttribute("style", "left:" + x + "px")
			}
		} else {
			if(N === "middle") {
				w.setAttribute("style", "display:none")
			}
		}
		c(A, "block", H, G)
	};
	e.createMask = function(y) {
		var x = o.createElement("div");
		x.classList.add(a);
		x.addEventListener("touchmove", e.preventDefault);
		x.addEventListener("tap", function() {
			w.close()
		});
		var w = [x];
		w._show = false;
		w.show = function() {
			w._show = true;
			x.setAttribute("style", "opacity:1");
			o.body.appendChild(x);
			return w
		};
		w._remove = function() {
			if(w._show) {
				w._show = false;
				x.setAttribute("style", "opacity:0");
				setTimeout(function() {
					o.body.removeChild(x)
				}, 350)
			}
			return w
		};
		w.close = function() {
			if(y) {
				if(y() !== false) {
					w._remove()
				}
			} else {
				w._remove()
			}
		};
		return w
	};
	e.fn.popover = function() {
		var w = arguments;
		this.each(function() {
			e.targets._popover = this;
			if(w[0] === "show" || w[0] === "hide" || w[0] === "toggle") {
				i(this, w[1])
			}
		})
	}
})(mui, window, document, "popover");
(function(f, h, i, b, d) {
	var e = "mui-control-item";
	var l = "mui-segmented-control";
	var j = "mui-control-content";
	var a = "mui-bar-tab";
	var k = "mui-tab-item";
	var c = "mui-slider-item";
	var g = function(m, n) {
		if(n.classList && (n.classList.contains(e) || n.classList.contains(k))) {
			m.preventDefault();
			return n
		}
		return false
	};
	f.registerTarget({
		name: b,
		index: 80,
		handle: g,
		target: false
	});
	h.addEventListener("tap", function(s) {
		var p = f.targets.tab;
		if(!p) {
			return
		}
		var m;
		var w;
		var x;
		var u = "mui-active";
		var n = "." + u;
		var t = p.parentNode;
		for(; t && t !== i; t = t.parentNode) {
			if(t.classList.contains(l)) {
				m = t.querySelector(n + "." + e);
				break
			} else {
				if(t.classList.contains(a)) {
					m = t.querySelector(n + "." + k)
				}
			}
		}
		if(m) {
			m.classList.remove(u)
		}
		var v = p === m;
		if(p) {
			p.classList.add(u)
		}
		if(!p.hash) {
			return
		}
		x = i.getElementById(p.hash.replace("#", ""));
		if(!x) {
			return
		}
		if(!x.classList.contains(j)) {
			p.classList[v ? "remove" : "add"](u);
			return
		}
		if(v) {
			return
		}
		var r = x.parentNode;
		w = r.querySelectorAll("." + j + n);
		for(var q = 0; q < w.length; q++) {
			var y = w[q];
			y.parentNode === r && y.classList.remove(u)
		}
		x.classList.add(u);
		var o = x.parentNode.querySelectorAll("." + j);
		f.trigger(x, f.eventName("shown", b), {
			tabNumber: Array.prototype.indexOf.call(o, x)
		});
		s.detail && s.detail.gesture.preventDefault()
	})
})(mui, window, document, "tab");
(function(f, i, b) {
	var e = "mui-switch";
	var c = "mui-switch-handle";
	var k = "mui-active";
	var a = "mui-dragging";
	var h = "mui-disabled";
	var d = "." + c;
	var g = function(l, m) {
		if(m.classList && m.classList.contains(e)) {
			return m
		}
		return false
	};
	f.registerTarget({
		name: b,
		index: 100,
		handle: g,
		target: false
	});
	var j = function(l) {
		this.element = l;
		this.classList = this.element.classList;
		this.handle = this.element.querySelector(d);
		this.init();
		this.initEvent()
	};
	j.prototype.init = function() {
		this.toggleWidth = this.element.offsetWidth;
		this.handleWidth = this.handle.offsetWidth;
		this.handleX = this.toggleWidth - this.handleWidth - 3
	};
	j.prototype.initEvent = function() {
		this.element.addEventListener("touchstart", this);
		this.element.addEventListener("drag", this);
		this.element.addEventListener("swiperight", this);
		this.element.addEventListener("touchend", this);
		this.element.addEventListener("touchcancel", this)
	};
	j.prototype.handleEvent = function(l) {
		if(this.classList.contains(h)) {
			return
		}
		switch(l.type) {
			case "touchstart":
				this.start(l);
				break;
			case "drag":
				this.drag(l);
				break;
			case "swiperight":
				this.swiperight();
				break;
			case "touchend":
			case "touchcancel":
				this.end(l);
				break
		}
	};
	j.prototype.start = function(l) {
		this.classList.add(a);
		if(this.toggleWidth === 0 || this.handleWidth === 0) {
			this.init()
		}
	};
	j.prototype.drag = function(m) {
		var l = m.detail;
		if(!this.isDragging) {
			if(l.direction === "left" || l.direction === "right") {
				this.isDragging = true;
				this.lastChanged = undefined;
				this.initialState = this.classList.contains(k)
			}
		}
		if(this.isDragging) {
			this.setTranslateX(l.deltaX);
			m.stopPropagation();
			l.gesture.preventDefault()
		}
	};
	j.prototype.swiperight = function(l) {
		if(this.isDragging) {
			l.stopPropagation()
		}
	};
	j.prototype.end = function(l) {
		this.classList.remove(a);
		if(this.isDragging) {
			this.isDragging = false;
			l.stopPropagation();
			f.trigger(this.element, "toggle", {
				isActive: this.classList.contains(k)
			})
		} else {
			this.toggle()
		}
	};
	j.prototype.toggle = function() {
		var l = this.classList;
		if(l.contains(k)) {
			l.remove(k);
			this.handle.style.webkitTransform = "translate3d(0,0,0)"
		} else {
			l.add(k);
			this.handle.style.webkitTransform = "translate3d(" + this.handleX + "px,0,0)"
		}
		f.trigger(this.element, "toggle", {
			isActive: this.classList.contains(k)
		})
	};
	j.prototype.setTranslateX = f.animationFrame(function(l) {
		if(!this.isDragging) {
			return
		}
		var m = false;
		if((this.initialState && -l > (this.handleX / 2)) || (!this.initialState && l > (this.handleX / 2))) {
			m = true
		}
		if(this.lastChanged !== m) {
			if(m) {
				this.handle.style.webkitTransform = "translate3d(" + (this.initialState ? 0 : this.handleX) + "px,0,0)";
				this.classList[this.initialState ? "remove" : "add"](k)
			} else {
				this.handle.style.webkitTransform = "translate3d(" + (this.initialState ? this.handleX : 0) + "px,0,0)";
				this.classList[this.initialState ? "add" : "remove"](k)
			}
			this.lastChanged = m
		}
	});
	f.fn["switch"] = function(m) {
		var l = [];
		this.each(function() {
			var n = null;
			var o = this.getAttribute("data-switch");
			if(!o) {
				o = ++f.uuid;
				f.data[o] = new j(this);
				this.setAttribute("data-switch", o)
			} else {
				n = f.data[o]
			}
			l.push(n)
		});
		return l.length > 1 ? l : l[0]
	};
	f.ready(function() {
		f("." + e)["switch"]()
	})
})(mui, window, "toggle");
(function(j, q, z) {
	var i = "mui-active";
	var w = "mui-selected";
	var G = "mui-grid-view";
	var p = "mui-table-view-radio";
	var b = "mui-table-view-cell";
	var D = "mui-collapse-content";
	var e = "mui-disabled";
	var y = "mui-switch";
	var o = "mui-btn";
	var B = "mui-slider-handle";
	var c = "mui-slider-left";
	var H = "mui-slider-right";
	var f = "mui-transitioning";
	var A = "." + B;
	var n = "." + c;
	var h = "." + H;
	var l = "." + w;
	var u = "." + o;
	var C = 0.8;
	var d, F;
	var s = isOpened = openedActions = progress = false;
	var E = sliderActionLeft = sliderActionRight = buttonsLeft = buttonsRight = sliderDirection = sliderRequestAnimationFrame = false;
	var t = translateX = lastTranslateX = sliderActionLeftWidth = sliderActionRightWidth = 0;
	var v = function(a) {
		if(a) {
			if(F) {
				F.classList.add(i)
			} else {
				if(d) {
					d.classList.add(i)
				}
			}
		} else {
			t && t.cancel();
			if(F) {
				F.classList.remove(i)
			} else {
				if(d) {
					d.classList.remove(i)
				}
			}
		}
	};
	var x = function() {
		if(translateX !== lastTranslateX) {
			if(buttonsRight && buttonsRight.length > 0) {
				progress = translateX / sliderActionRightWidth;
				if(translateX < -sliderActionRightWidth) {
					translateX = -sliderActionRightWidth - Math.pow(-translateX - sliderActionRightWidth, C)
				}
				for(var I = 0, a = buttonsRight.length; I < a; I++) {
					var K = buttonsRight[I];
					if(typeof K._buttonOffset === "undefined") {
						K._buttonOffset = K.offsetLeft
					}
					buttonOffset = K._buttonOffset;
					g(K, (translateX - buttonOffset * (1 + Math.max(progress, -1))))
				}
			}
			if(buttonsLeft && buttonsLeft.length > 0) {
				progress = translateX / sliderActionLeftWidth;
				if(translateX > sliderActionLeftWidth) {
					translateX = sliderActionLeftWidth + Math.pow(translateX - sliderActionLeftWidth, C)
				}
				for(var I = 0, a = buttonsLeft.length; I < a; I++) {
					var J = buttonsLeft[I];
					if(typeof J._buttonOffset === "undefined") {
						J._buttonOffset = sliderActionLeftWidth - J.offsetLeft - J.offsetWidth
					}
					buttonOffset = J._buttonOffset;
					if(buttonsLeft.length > 1) {
						J.style.zIndex = buttonsLeft.length - I
					}
					g(J, (translateX + buttonOffset * (1 - Math.min(progress, 1))))
				}
			}
			g(E, translateX);
			lastTranslateX = translateX
		}
		sliderRequestAnimationFrame = requestAnimationFrame(function() {
			x()
		})
	};
	var g = function(I, a) {
		if(I) {
			I.style.webkitTransform = "translate3d(" + a + "px,0,0)"
		}
	};
	q.addEventListener("touchstart", function(K) {
		if(d) {
			v(false)
		}
		d = F = false;
		s = isOpened = openedActions = false;
		var M = K.target;
		var a = false;
		for(; M && M !== z; M = M.parentNode) {
			if(M.classList) {
				var N = M.classList;
				if((M.tagName === "INPUT" && M.type !== "radio" && M.type !== "checkbox") || M.tagName === "BUTTON" || N.contains(y) || N.contains(o) || N.contains(e)) {
					a = true
				}
				if(N.contains(D)) {
					break
				}
				if(N.contains(b)) {
					d = M;
					var I = d.parentNode.querySelector(l);
					if(!d.parentNode.classList.contains(p) && I && I !== d) {
						j.swipeoutClose(I);
						d = a = false;
						return
					}
					if(!d.parentNode.classList.contains(G)) {
						var J = d.querySelector("a");
						if(J && J.parentNode === d) {
							F = J
						}
					}
					var L = d.querySelector(A);
					if(L) {
						k(d);
						K.stopPropagation()
					}
					if(!a) {
						if(L) {
							if(t) {
								t.cancel()
							}
							t = j.later(function() {
								v(true)
							}, 100)
						} else {
							if(!(d.querySelector("input") || d.querySelector(u) || d.querySelector("." + y))) {
								v(true)
							}
						}
					}
					break
				}
			}
		}
	});
	q.addEventListener("touchmove", function(a) {
		v(false)
	});
	var r = {
		handleEvent: function(a) {
			switch(a.type) {
				case "drag":
					this.drag(a);
					break;
				case "dragend":
					this.dragend(a);
					break;
				case "flick":
					this.flick(a);
					break;
				case "swiperight":
					this.swiperight(a);
					break;
				case "swipeleft":
					this.swipeleft(a);
					break
			}
		},
		drag: function(I) {
			if(!d) {
				return
			}
			if(!s) {
				E = sliderActionLeft = sliderActionRight = buttonsLeft = buttonsRight = sliderDirection = sliderRequestAnimationFrame = false;
				E = d.querySelector(A);
				if(E) {
					sliderActionLeft = d.querySelector(n);
					sliderActionRight = d.querySelector(h);
					if(sliderActionLeft) {
						sliderActionLeftWidth = sliderActionLeft.offsetWidth;
						buttonsLeft = sliderActionLeft.querySelectorAll(u)
					}
					if(sliderActionRight) {
						sliderActionRightWidth = sliderActionRight.offsetWidth;
						buttonsRight = sliderActionRight.querySelectorAll(u)
					}
					d.classList.remove(f);
					isOpened = d.classList.contains(w);
					if(isOpened) {
						openedActions = d.querySelector(n + l) ? "left" : "right"
					}
				}
			}
			var a = I.detail;
			var J = a.direction;
			var L = a.angle;
			if(J === "left" && (L > 150 || L < -150)) {
				if(buttonsRight || (buttonsLeft && isOpened)) {
					s = true
				}
			} else {
				if(J === "right" && (L > -30 && L < 30)) {
					if(buttonsLeft || (buttonsRight && isOpened)) {
						s = true
					}
				}
			}
			if(s) {
				I.stopPropagation();
				I.detail.gesture.preventDefault();
				var K = I.detail.deltaX;
				if(isOpened) {
					if(openedActions === "right") {
						K = K - sliderActionRightWidth
					} else {
						K = K + sliderActionLeftWidth
					}
				}
				if((K > 0 && !buttonsLeft) || (K < 0 && !buttonsRight)) {
					if(!isOpened) {
						return
					}
					K = 0
				}
				if(K < 0) {
					sliderDirection = "toLeft"
				} else {
					if(K > 0) {
						sliderDirection = "toRight"
					} else {
						if(!sliderDirection) {
							sliderDirection = "toLeft"
						}
					}
				}
				if(!sliderRequestAnimationFrame) {
					x()
				}
				translateX = K
			}
		},
		flick: function(a) {
			if(s) {
				a.stopPropagation()
			}
		},
		swipeleft: function(a) {
			if(s) {
				a.stopPropagation()
			}
		},
		swiperight: function(a) {
			if(s) {
				a.stopPropagation()
			}
		},
		dragend: function(J) {
			if(!s) {
				return
			}
			J.stopPropagation();
			if(sliderRequestAnimationFrame) {
				cancelAnimationFrame(sliderRequestAnimationFrame);
				sliderRequestAnimationFrame = null
			}
			var R = J.detail;
			s = false;
			var N = "close";
			var I = sliderDirection === "toLeft" ? sliderActionRightWidth : sliderActionLeftWidth;
			var a = R.swipe || (Math.abs(translateX) > I / 2);
			if(a) {
				if(!isOpened) {
					N = "open"
				} else {
					if(R.direction === "left" && openedActions === "right") {
						N = "open"
					} else {
						if(R.direction === "right" && openedActions === "left") {
							N = "open"
						}
					}
				}
			}
			d.classList.add(f);
			var T;
			if(N === "open") {
				var L = sliderDirection === "toLeft" ? -I : I;
				g(E, L);
				T = sliderDirection === "toLeft" ? buttonsRight : buttonsLeft;
				if(typeof T !== "undefined") {
					var Q = null;
					for(var O = 0; O < T.length; O++) {
						Q = T[O];
						g(Q, L)
					}
					Q.parentNode.classList.add(w);
					d.classList.add(w);
					if(!isOpened) {
						j.trigger(d, sliderDirection === "toLeft" ? "slideleft" : "slideright")
					}
				}
			} else {
				g(E, 0);
				sliderActionLeft && sliderActionLeft.classList.remove(w);
				sliderActionRight && sliderActionRight.classList.remove(w);
				d.classList.remove(w)
			}
			var M;
			if(buttonsLeft && buttonsLeft.length > 0 && buttonsLeft !== T) {
				for(var O = 0, S = buttonsLeft.length; O < S; O++) {
					var K = buttonsLeft[O];
					M = K._buttonOffset;
					if(typeof M === "undefined") {
						K._buttonOffset = sliderActionLeftWidth - K.offsetLeft - K.offsetWidth
					}
					g(K, M)
				}
			}
			if(buttonsRight && buttonsRight.length > 0 && buttonsRight !== T) {
				for(var O = 0, S = buttonsRight.length; O < S; O++) {
					var P = buttonsRight[O];
					M = P._buttonOffset;
					if(typeof M === "undefined") {
						P._buttonOffset = P.offsetLeft
					}
					g(P, -M)
				}
			}
		}
	};

	function k(a, I) {
		var J = !!I ? "removeEventListener" : "addEventListener";
		a[J]("drag", r);
		a[J]("dragend", r);
		a[J]("swiperight", r);
		a[J]("swipeleft", r);
		a[J]("flick", r)
	}
	j.swipeoutOpen = function(K, Q) {
		if(!K) {
			return
		}
		var I = K.classList;
		if(I.contains(w)) {
			return
		}
		if(!Q) {
			if(K.querySelector(h)) {
				Q = "right"
			} else {
				Q = "left"
			}
		}
		var a = K.querySelector(j.classSelector(".slider-" + Q));
		if(!a) {
			return
		}
		a.classList.add(w);
		I.add(w);
		I.remove(f);
		var P = a.querySelectorAll(u);
		var O = a.offsetWidth;
		var L = (Q === "right") ? -O : O;
		var J = P.length;
		var N;
		for(var M = 0; M < J; M++) {
			N = P[M];
			if(Q === "right") {
				g(N, -N.offsetLeft)
			} else {
				g(N, (O - N.offsetWidth - N.offsetLeft))
			}
		}
		I.add(f);
		for(var M = 0; M < J; M++) {
			g(P[M], L)
		}
		g(K.querySelector(A), L)
	};
	j.swipeoutClose = function(K) {
		if(!K) {
			return
		}
		var I = K.classList;
		if(!I.contains(w)) {
			return
		}
		var P = K.querySelector(h + l) ? "right" : "left";
		var a = K.querySelector(j.classSelector(".slider-" + P));
		if(!a) {
			return
		}
		a.classList.remove(w);
		I.remove(w);
		I.add(f);
		var O = a.querySelectorAll(u);
		var N = a.offsetWidth;
		var J = O.length;
		var M;
		g(K.querySelector(A), 0);
		for(var L = 0; L < J; L++) {
			M = O[L];
			if(P === "right") {
				g(M, (-M.offsetLeft))
			} else {
				g(M, (N - M.offsetWidth - M.offsetLeft))
			}
		}
	};
	q.addEventListener("touchend", function(a) {
		if(!d) {
			return
		}
		v(false);
		E && k(d, true)
	});
	q.addEventListener("touchcancel", function(a) {
		if(!d) {
			return
		}
		v(false);
		E && k(d, true)
	});
	var m = function() {
		var I = d.classList;
		if(I.contains("mui-radio")) {
			var a = d.querySelector("input[type=radio]");
			if(a) {
				a.click()
			}
		} else {
			if(I.contains("mui-checkbox")) {
				var a = d.querySelector("input[type=checkbox]");
				if(a) {
					a.click()
				}
			}
		}
	};
	q.addEventListener(j.EVENT_CLICK, function(a) {
		if(d && d.classList.contains("mui-collapse")) {
			a.preventDefault()
		}
	});
	q.addEventListener("doubletap", function(a) {
		if(d) {
			m()
		}
	});
	q.addEventListener("tap", function(K) {
		if(!d) {
			return
		}
		var a = false;
		var M = d.classList;
		var I = d.parentNode;
		if(I.classList.contains(p)) {
			if(M.contains(w)) {
				return
			}
			var J = I.querySelector("li" + l);
			if(J) {
				J.classList.remove(w)
			}
			M.add(w);
			j.trigger(d, "selected", {
				el: d
			});
			return
		}
		if(M.contains("mui-collapse") && !d.parentNode.classList.contains("mui-unfold")) {
			K.detail.gesture.preventDefault();
			if(!M.contains(i)) {
				var L = d.parentNode.querySelector(".mui-collapse.mui-active");
				if(L) {
					L.classList.remove(i)
				}
				a = true
			}
			M.toggle(i);
			if(a) {
				j.trigger(d, "expand")
			}
		}
		m()
	})
})(mui, window, document);
(function(b, a) {
	b.alert = function(c, e, d, f) {
		if(b.os.plus) {
			if(typeof c === undefined) {
				return
			} else {
				if(typeof e === "function") {
					f = e;
					e = null;
					d = "确定"
				} else {
					if(typeof d === "function") {
						f = d;
						d = null
					}
				}
				plus.nativeUI.alert(c, f, e, d)
			}
		} else {
			a.alert(c)
		}
	}
})(mui, window);
(function(b, a) {
	b.confirm = function(d, e, c, f) {
		if(b.os.plus) {
			if(typeof d === undefined) {
				return
			} else {
				if(typeof e === "function") {
					f = e;
					e = null;
					c = null
				} else {
					if(typeof c === "function") {
						f = c;
						c = null
					}
				}
				plus.nativeUI.confirm(d, f, e, c)
			}
		} else {
			a.confirm(d)
		}
	}
})(mui, window);
(function(b, a) {
	b.prompt = function(f, d, e, c, g) {
		if(b.os.plus) {
			if(typeof message === undefined) {
				return
			} else {
				if(typeof d === "function") {
					g = d;
					d = null;
					e = null;
					c = null
				} else {
					if(typeof e === "function") {
						g = e;
						e = null;
						c = null
					} else {
						if(typeof c === "function") {
							g = c;
							c = null
						}
					}
				}
				plus.nativeUI.prompt(f, g, e, d, c)
			}
		} else {
			a.prompt(f)
		}
	}
})(mui, window);
(function(b, a) {
	b.toast = function(d) {
		if(b.os.plus) {
			plus.nativeUI.toast(d, {
				verticalAlign: "bottom"
			})
		} else {
			var c = document.createElement("div");
			c.classList.add("mui-toast-container");
			c.innerHTML = '<div class="mui-toast-message">' + d + "</div>";
			document.body.appendChild(c);
			setTimeout(function() {
				document.body.removeChild(c)
			}, 2000)
		}
	}
})(mui, window);
(function(d, f, l) {
	var q = "mui-icon";
	var b = "mui-icon-clear";
	var e = "mui-icon-speech";
	var a = "mui-icon-search";
	var i = "mui-input-row";
	var c = "mui-placeholder";
	var k = "mui-tooltip";
	var n = "mui-hidden";
	var r = "mui-focusin";
	var h = "." + b;
	var j = "." + e;
	var g = "." + c;
	var p = "." + k;
	var m = function(s) {
		for(; s && s !== l; s = s.parentNode) {
			if(s.classList && s.classList.contains(i)) {
				return s
			}
		}
		return null
	};
	var o = function(t, s) {
		this.element = t;
		this.options = s || {
			actions: "clear"
		};
		if(~this.options.actions.indexOf("slider")) {
			this.sliderActionClass = k + " " + n;
			this.sliderActionSelector = p
		} else {
			if(~this.options.actions.indexOf("clear")) {
				this.clearActionClass = q + " " + b + (t.value ? "" : (" " + n));
				this.clearActionSelector = h
			}
			if(~this.options.actions.indexOf("speech")) {
				this.speechActionClass = q + " " + e;
				this.speechActionSelector = j
			}
			if(~this.options.actions.indexOf("search")) {
				this.searchActionClass = c;
				this.searchActionSelector = g
			}
		}
		this.init()
	};
	o.prototype.init = function() {
		this.initAction();
		this.initElementEvent()
	};
	o.prototype.initAction = function() {
		var s = this;
		var t = s.element.parentNode;
		if(t) {
			if(s.sliderActionClass) {
				s.sliderAction = s.createAction(t, s.sliderActionClass, s.sliderActionSelector)
			} else {
				if(s.searchActionClass) {
					s.searchAction = s.createAction(t, s.searchActionClass, s.searchActionSelector);
					s.searchAction.addEventListener("tap", function(u) {
						d.focus(s.element);
						u.stopPropagation()
					})
				}
				if(s.speechActionClass) {
					s.speechAction = s.createAction(t, s.speechActionClass, s.speechActionSelector);
					s.speechAction.addEventListener("click", d.stopPropagation);
					s.speechAction.addEventListener("tap", function(u) {
						s.speechActionClick(u)
					})
				}
				if(s.clearActionClass) {
					s.clearAction = s.createAction(t, s.clearActionClass, s.clearActionSelector);
					s.clearAction.addEventListener("tap", function(u) {
						s.clearActionClick(u)
					})
				}
			}
		}
	};
	o.prototype.createAction = function(v, u, s) {
		var t = v.querySelector(s);
		if(!t) {
			var t = l.createElement("span");
			t.className = u;
			if(u === this.searchActionClass) {
				t.innerHTML = '<span class="' + q + " " + a + '"></span>' + this.element.getAttribute("placeholder");
				this.element.setAttribute("placeholder", "");
				if(this.element.value.trim()) {
					v.classList.add("mui-active")
				}
			}
			v.insertBefore(t, this.element.nextSibling)
		}
		return t
	};
	o.prototype.initElementEvent = function() {
		var x = this.element;
		if(this.sliderActionClass) {
			var A = this.sliderAction;
			var u = x.offsetLeft;
			var t = x.offsetWidth - 28;
			var y = A.offsetWidth;
			var z = Math.abs(x.max - x.min);
			var s = null;
			var w = function() {
				A.classList.remove(n);
				y = y || A.offsetWidth;
				var B = Math.abs(x.value) / z * t;
				A.style.left = (14 + u + B - y / 2) + "px";
				A.innerText = x.value;
				if(s) {
					clearTimeout(s)
				}
				s = setTimeout(function() {
					A.classList.add(n)
				}, 1000)
			};
			x.addEventListener("input", w);
			x.addEventListener("tap", w);
			x.addEventListener("touchmove", function(B) {
				B.stopPropagation()
			})
		} else {
			if(this.clearActionClass) {
				var v = this.clearAction;
				if(!v) {
					return
				}
				d.each(["keyup", "change", "input", "focus", "blur", "cut", "paste"], function(B, C) {
					(function(D) {
						x.addEventListener(D, function() {
							v.classList[x.value.trim() ? "remove" : "add"](n)
						})
					})(C)
				})
			}
			if(this.searchActionClass) {
				x.addEventListener("focus", function() {
					x.parentNode.classList.add("mui-active")
				});
				x.addEventListener("blur", function() {
					if(!x.value.trim()) {
						x.parentNode.classList.remove("mui-active")
					}
				})
			}
		}
	};
	o.prototype.clearActionClick = function(t) {
		var s = this;
		s.element.value = "";
		d.focus(s.element);
		s.clearAction.classList.add(n);
		t.preventDefault()
	};
	o.prototype.speechActionClick = function(u) {
		if(f.plus) {
			var s = this;
			var t = s.element.value;
			s.element.value = "";
			l.body.classList.add(r);
			plus.speech.startRecognize({
				engine: "iFly"
			}, function(v) {
				s.element.value += v;
				d.focus(s.element);
				plus.speech.stopRecognize();
				d.trigger(s.element, "recognized", {
					value: s.element.value
				});
				if(t !== s.element.value) {
					d.trigger(s.element, "change");
					d.trigger(s.element, "input")
				}
			}, function(v) {
				l.body.classList.remove(r)
			})
		} else {
			alert("only for 5+")
		}
		u.preventDefault()
	};
	d.fn.input = function(s) {
		this.each(function() {
			var x = [];
			var w = m(this.parentNode);
			if(this.type === "range" && w.classList.contains("mui-input-range")) {
				x.push("slider")
			} else {
				var v = this.classList;
				if(v.contains("mui-input-clear")) {
					x.push("clear")
				}
				if(v.contains("mui-input-speech")) {
					x.push("speech")
				}
				if(this.type === "search" && w.classList.contains("mui-search")) {
					x.push("search")
				}
			}
			var y = this.getAttribute("data-input-" + x[0]);
			if(!y) {
				y = ++d.uuid;
				d.data[y] = new o(this, {
					actions: x.join(",")
				});
				for(var u = 0, t = x.length; u < t; u++) {
					this.setAttribute("data-input-" + x[u], y)
				}
			}
		})
	};
	d.ready(function() {
		d(".mui-input-row input").input()
	})
})(mui, window, document);
(function(c) {
	var b = ("ontouchstart" in document);
	var h = b ? "tap" : "click";
	var e = "change";
	var i = "mui-numbox";
	var f = "mui-numbox-btn-plus";
	var a = "mui-numbox-btn-minus";
	var d = "mui-numbox-input";
	var g = c.Numbox = c.Class.extend({
		init: function(l, k) {
			var j = this;
			k = k || {};
			k.step = parseInt(k.step || 1);
			j.options = k;
			j.holder = l;
			j.input = c.qsa("." + d, j.holder)[0];
			j.plus = c.qsa("." + f, j.holder)[0];
			j.minus = c.qsa("." + a, j.holder)[0];
			j.checkValue();
			j.initEvent()
		},
		initEvent: function() {
			var j = this;
			j.plus.addEventListener(h, function(k) {
				var l = parseInt(j.input.value) + j.options.step;
				j.input.value = l.toString();
				c.trigger(j.input, e, null)
			});
			j.minus.addEventListener(h, function(k) {
				var l = parseInt(j.input.value) - j.options.step;
				j.input.value = l.toString();
				c.trigger(j.input, e, null)
			});
			j.input.addEventListener(e, function(k) {
				j.checkValue()
			})
		},
		checkValue: function() {
			var j = this;
			var k = j.input.value;
			if(k == null || k == "" || isNaN(k)) {
				j.input.value = j.options.min || 0;
				j.minus.disabled = j.options.min != null
			} else {
				var k = parseInt(k);
				if(j.options.max != null && !isNaN(j.options.max) && k >= parseInt(j.options.max)) {
					k = j.options.max;
					j.plus.disabled = true
				} else {
					j.plus.disabled = false
				}
				if(j.options.min != null && !isNaN(j.options.min) && k <= parseInt(j.options.min)) {
					k = j.options.min;
					j.minus.disabled = true
				} else {
					j.minus.disabled = false
				}
				j.input.value = k
			}
		}
	});
	c.fn.numbox = function(j) {
		this.each(function(n, m) {
			if(l) {
				new g(m, l)
			} else {
				var k = m.getAttribute("data-numbox-options");
				var l = k ? JSON.parse(k) : {};
				l.step = m.getAttribute("data-numbox-step") || l.step;
				l.min = m.getAttribute("data-numbox-min") || l.min;
				l.max = m.getAttribute("data-numbox-max") || l.max;
				new g(m, l)
			}
		});
		return this
	};
	c.ready(function() {
		c("." + i).numbox()
	})
}(mui));