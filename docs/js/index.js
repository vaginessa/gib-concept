"use strict";

// functions
// window size function
function wndsize() {
	var w = 0;
	var h = 0;
	//IE
	if (!window.innerWidth) {
		if (!(document.documentElement.clientWidth == 0)) {
			//strict mode
			w = document.documentElement.clientWidth;
			h = document.documentElement.clientHeight;
		} else {
			//quirks mode
			w = document.body.clientWidth;
			h = document.body.clientHeight;
		}
	} else {
		//w3c
		w = window.innerWidth;
		h = window.innerHeight;
	}
	return {
		width: w,
		height: h
	};
}

// map function
Number.prototype.map = function (in_min, in_max, out_min, out_max) {
	return (this - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
};

// vars
var doc = document,
    page = doc.getElementById('page'),
    timeAnim = 1.25;

//REACT
var Ripple = React.createClass({
	displayName: "Ripple",

	getInitialState: function getInitialState() {
		return {
			x: "",
			y: "",
			w: wndsize().width,
			h: wndsize().height
		};
	},
	rippleAnim: function rippleAnim(event) {

		var dom = this.refs.ripple.getDOMNode(),
		    greenDom = this.refs.greenripple.getDOMNode(),
		    tl = new TimelineMax(),
		    offsetX = Math.abs(this.state.w / 2 - event.pageX),
		    offsetY = Math.abs(this.state.h / 2 - event.pageY),
		    deltaX = this.state.w / 2 + offsetX,
		    deltaY = this.state.h / 2 + offsetY,
		    scale_ratio = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));

		TweenMax.set([dom, greenDom], { transformOrigin: "center center" });

		tl.to(dom, timeAnim, {
			attr: {
				r: scale_ratio
			},
			ease: Power3.easeOut,
			onComplete: function onComplete() {
				classie.add(page, "orange");
			}
		}).to(dom, 2 * timeAnim, {
			attr: {
				r: 32
			},
			delay: timeAnim / 3,
			ease: Power0.easeNone
		}).to(greenDom, timeAnim / 2, {
			attr: {
				r: scale_ratio
			},
			delay: timeAnim / 3,
			ease: Power3.easeOut
		});
	},
	componentWillReceiveProps: function componentWillReceiveProps(nextProps) {
		if (nextProps.activity === "play") {

			switch (nextProps.point) {
				case "one":
					this.setState({
						x: nextProps.event.pageX,
						y: nextProps.event.pageY
					});
					this.rippleAnim(nextProps.event);
					break;
				case "two":
					var dom = this.refs.ripple.getDOMNode(),
					    greenDom = this.refs.greenripple.getDOMNode(),
					    tl = new TimelineMax(),
					    offsetX = Math.abs(this.state.w / 2 - this.state.x),
					    offsetY = Math.abs(this.state.h / 2 - this.state.y),
					    deltaX = this.state.w / 2 + offsetX,
					    deltaY = this.state.h / 2 + offsetY,
					    scale_ratio = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));

					tl.to(dom, timeAnim, {
						attr: {
							r: scale_ratio
						},
						onComplete: function onComplete() {
							classie.remove(page, "orange");
							TweenMax.set(greenDom, {
								attr: {
									r: 32
								}
							});
						},
						ease: Power3.easeOut
					}).to(dom, timeAnim / 2, {
						attr: {
							r: 32
						},
						ease: Power3.easeOut
					});
					break;
			}
		}
	},
	render: function render() {
		return React.createElement(
			"svg",
			{ height: "1", width: "1" },
			React.createElement("circle", { ref: "greenripple", id: "green_ripple", cx: "0", cy: "0", r: "32" }),
			React.createElement("circle", { ref: "ripple", id: "white_ripple", cx: "0", cy: "0", r: "32" })
		);
	}

});

var Button = React.createClass({
	displayName: "Button",

	handleClick: function handleClick(e) {
		var self = this;
		if (this.state.action === "paused") {
			this.setState({
				action: "play",
				point: "one",
				progress: 0,
				event: e.nativeEvent
			});
			var arrow = this.refs.arrow_icon.getDOMNode(),
			    done = this.refs.done_icon.getDOMNode(),
			    progress = this.refs.progress.getDOMNode(),
			    tl = new TimelineMax();

			tl.fromTo(arrow, timeAnim, {
				yPercent: 0,
				autoAlpha: 1,
				scale: 1
			}, {
				yPercent: 20,
				autoAlpha: 0,
				//delay: timeAnim/3,
				ease: Power3.easeOut
			}).fromTo(progress, 2 * timeAnim / 3, {
				yPercent: -20,
				autoAlpha: 0,
				scale: 0.6
			}, {
				yPercent: 0,
				autoAlpha: 1,
				scale: 1,
				ease: Power3.easeOut
			}, "-=" + timeAnim / 3).to(self.state, 2 * timeAnim, {
				progress: 100,
				ease: Power0.easeNone,
				onUpdate: function onUpdate(tween) {
					self.setState({
						progress: parseInt(tween.target.progress),
						action: "paused"
					});
				},
				onUpdateParams: ["{self}"]
			}).to(progress, timeAnim / 4, {
				yPercent: 20,
				autoAlpha: 0,
				scale: 0.6,
				delay: timeAnim / 3,
				ease: Power3.easeOut
			}).fromTo(done, timeAnim / 4, {
				yPercent: -20,
				autoAlpha: 0,
				scale: 0.6
			}, {
				yPercent: 0,
				autoAlpha: 1,
				scale: 1,
				ease: Power3.easeOut
			}).to(done, 2 * timeAnim / 3, {
				yPercent: 20,
				autoAlpha: 0,
				scale: 0.6,
				delay: timeAnim / 3,
				onStart: function onStart() {
					self.setState({
						action: "play",
						point: "two",
						progress: 0,
						event: ""
					});
				},
				ease: Power3.easeOut
			}).fromTo(arrow, 2 * timeAnim / 3, {
				yPercent: -20,
				scale: 0.6,
				autoAlpha: 0
			}, {
				yPercent: 0,
				scale: 1,
				autoAlpha: 1,
				delay: timeAnim / 2,
				ease: Power3.easeOut,
				onComplete: function onComplete() {
					self.setState({
						action: "paused",
						point: "one",
						progress: 0,
						event: ""
					});
				}
			});
		}
	},
	getInitialState: function getInitialState() {
		return {
			action: "paused",
			point: "",
			progress: 0,
			event: ""
		};
	},
	render: function render() {
		return React.createElement(
			"div",
			{ className: "material_button", onClick: this.handleClick },
			React.createElement(
				"i",
				{ ref: "done_icon", className: "material-icons done" },
				"done"
			),
			React.createElement(
				"div",
				{ ref: "progress", className: "progress" },
				this.state.progress
			),
			React.createElement(
				"i",
				{ ref: "arrow_icon", className: "material-icons" },
				"arrow_downward"
			),
			React.createElement(Ripple, { activity: this.state.action, event: this.state.event, point: this.state.point })
		);
	}

});

React.render(React.createElement(Button, null), page);