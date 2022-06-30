<style lang="less">
	@import './login.less';
  .login-tip{
    text-align: center;
  }
</style>

<template>
	<div class="login">
		<div class="login-con">
			<Card icon="log-in" title="欢迎登录" :bordered="false">
				<div class="form-con">
					<login-form @on-success-valid="handleSubmit"></login-form>
					<p class="login-tip">{{msg}}</p>
				</div>
			</Card>
		</div>
	</div>
</template>

<script>
	import LoginForm from '@/components/login-form'
	import {
		mapActions
	} from 'vuex'
  import {getUserInfoByToken} from "@/api/user";
	export default {
    data(){
      return{
        msg:'输入任意用户名和密码即可'
      }
    },
		components: {
			LoginForm
		},
		methods: {
			...mapActions([
				'handleLogin'
			]),
			handleSubmit({
				userName,
				password,
				token
			}) {
				this.handleLogin({
					userName,
					password
				}).then(res => {
          if(res.data.success){
            getUserInfoByToken({token}).then(res => {
              this.$router.push({
                name: this.$config.homeName
              })
            })
          }else{
            this.msg = res.data.msg;
          }


				})
			}
		}
	}
</script>

<style>

</style>
