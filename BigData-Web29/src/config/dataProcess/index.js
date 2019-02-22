/**
 * Created by yue.hao on 2017/7/10.
 */
import ui from "./ui"
import algorithm from "./algorithm"

window._parse = function(data) {
    console.log(JSON.parse(JSON.stringify(data)))
}

export default Object.assign(ui, algorithm)