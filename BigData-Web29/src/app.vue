<template>
	<div id="app" class="blue" v-scroll>
		<div class="app_container">
			<router-view>
			</router-view>
		</div>
	</div>
</template>

<script>
	export default {
		name: 'app',
		created() {
			this.checkSize()
			this.changeSize()
		},
		methods: {
			checkSize() {
				if(this.$.clientWidth() < 1300) {
					this.$notify({
						title: '提示',
						message: '为了获得更好的使用效果,建议更换1366*768及以上屏幕分辨率使用(ctrl + "-" 可以快速调整尺寸)',
						type: 'warning',
						duration: 0
					});
					return false
				}
				return true
			},
			changeSize() {
				this.$.addResizeLisener()
			}
		}
	}
</script>