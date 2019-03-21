import Vue from 'vue'
import DataProcess from '@/config/dataProcess'

Vue.directive('scroll', {
    // 当绑定元素插入到 DOM 中。
    inserted: function(el, binding) {
        let options = binding.expression ? JSON.parse(binding.expression) : {
            "axis": "yx",
            "theme": "dark",
            "scrollInertia": 300
        }
        setTimeout(() => {
            $(el).mCustomScrollbar(options);
        })
    }
})
Vue.directive('dialog-drag', {
    bind: function(el, binding, vnode, oldVnode) {
        let header = el.querySelector(".el-dialog__header")
        let body = el.querySelector(".el-dialog")
        header.onmousedown = (e) => {
            if(e.button != 0) {
                return false
            }
            {
                if(!body.isInitDrag) {
                    if(body.className.indexOf("full") != -1) {
                    	body.style.cssText =
                    	`transform:none;
                    	width:${body.offsetWidth}px;
                    	height:${body.offsetHeight}px;
                    	left:${document.body.offsetWidth / 2 - body.offsetWidth / 2}px;
                    	top:${body.offsetTop}px;
                    	right:"auto";
                    	bottom:"auto"`
                    } else {
                    	body.style.cssText =
                    	`transform:none;
                    	left:${document.body.offsetWidth / 2 - body.offsetWidth / 2}px;
                    	top:${body.offsetTop}px;
                    	right:"auto";
                    	bottom:"auto"`
                    }
                    body.isInitDrag = true
                }
            }
            window.onselectstart = window.ondragstart = () => { return false };
            let mouse_pos = DataProcess.mousePosition(e)
            let dom_pos = DataProcess.getElCoordinate(body)
            let offsetX = mouse_pos.x - dom_pos.left
            let offsetY = mouse_pos.y - dom_pos.top
            let X, Y
            window.onmousemove = DataProcess.throttle((e) => {
                let mouse_position = DataProcess.mousePosition(e)
 X = mouse_position.x - offsetX
 Y = mouse_position.y - offsetY
 X = X >= 0 ? X : 0
 Y = Y >= 0 ? Y : 0
 body.style.left= X + "px"
                body.style.top = Y + "px"
            }, 20)
            window.onmouseup = () => {
                window.onmouseup = window.onmousemove = window.onselectstart = window.ondragstart = null
            }
        }
    }
})
