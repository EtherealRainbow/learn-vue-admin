import Main from '@/components/main'
/**
 * iview-admin中meta除了原生参数外可配置的参数:
 * meta: {
 *  title: { String|Number|Function }
 *         显示在侧边栏、面包屑和标签栏的文字
 *         使用'{{ 多语言字段 }}'形式结合多语言使用，例子看多语言的路由配置;
 *         可以传入一个回调函数，参数是当前路由对象，例子看动态路由和带参路由
 *  hideInBread: (false) 设为true后此级路由将不会出现在面包屑中，示例看QQ群路由配置
 *  hideInMenu: (false) 设为true后在左侧菜单不会显示该页面选项
 *  notCache: (false) 设为true后页面在切换标签后不会缓存，如果需要缓存，无需设置这个字段，而且需要设置页面组件name属性和路由配置的name一致
 *  access: (null) 可访问该页面的权限数组，当前路由设置的权限会影响子路由
 *  icon: (-) 该页面在左侧菜单、面包屑和标签导航处显示的图标，如果是自定义图标，需要在图标名称前加下划线'_'
 *  beforeCloseName: (-) 设置该字段，则在关闭当前tab页时会去'@/router/before-close.js'里寻找该字段名对应的方法，作为关闭前的钩子函数
 * }
 */

export default [{
		path: '/login',
		name: 'login',
		meta: {
			title: 'Login - 登录',
			hideInMenu: true
		},
		component: () => import('@/view/login/login.vue')
	},
	{
		path: '/ftpView',
		name: 'FPT查看',
		meta: {
			requireAuth: false,
			icon: 'md-arrow-dropdown-circle',
			hideInMenu:true,
			title: 'FPT查看'
		},
		component: () => import('@/view/ftp/ftpView.vue')
	},
	{
		path: '/',
		name: '_home',
		redirect: '/home',
		component: Main,
		meta: {
			hideInMenu: true,
			notCache: true
		},
		children: [{
			path: '/home',
			name: 'home',
			meta: {
				hideInMenu: true,
				title: '首页',
				notCache: true,
				icon: 'md-home'
			},
			component: () => import('@/view/single-page/home')
		}]
	},
	{
		path: '/components',
		name: 'FTP管理',
		meta: {
			icon: 'logo-buffer',
			title: 'FTP管理'
		},
		component: Main,
		children: [{
				path: 'ftpManage',
				name: '服务器',
				meta: {
					icon: 'md-cloud',
					title: 'FPT服务器'
				},
				component: () => import('@/view/ftp/ftpManage.vue')
			},
			{
				path: 'ftpView',
				name: '文件管理',
				meta: {
					equireAuth: false,
					icon: 'md-folder',
					title: '文件管理'
				},
				component: () => import('@/view/ftp/ftpView.vue')
			}
		]
	},
	{
		path: '/task',
		name: '任务调度',
		meta: {
			icon: 'logo-buffer',
			title: '任务调度管理'
		},
		component: Main,
		children: [{
				path: 'quartzServer',
				name: '任务服务器',
				meta: {
					title: '任务服务器',
					icon: 'md-cloud'
				},
				component: () => import('@/view/quartz/quartzServer.vue')
			},
			{
				path: 'quartzConfig',
				name: '定时任务设置',
				meta: {
					equireAuth: false,
					title: '定时任务设置',
					icon: 'md-arrow-dropdown-circle'
				},
				component: () => import('@/view/quartz/quartzConfig.vue')
			}
		]
	},
	{
		path: '/auth',
		name: '权限',
		meta: {
			icon: 'logo-buffer',
			title: '权限管理'
		},
		component: Main,
		children: [{
			path: 'menuList',
			name: '菜单列表',
			meta: {
				equireAuth: false,
				title: '菜单列表',
				icon: 'md-menu'
			},
			component: () => import('@/view/auth/menuList.vue')
		}, {
			path: 'userList',
			name: '用户列表',
			meta: {
				title: '用户列表',
				icon: 'md-people'
			},
			component: () => import('@/view/auth/userList.vue')
		}, {
			path: 'roleList',
			name: '角色列表',
			meta: {
				equireAuth: false,
				title: '角色列表',
				icon: 'md-settings'
			},
			component: () => import('@/view/auth/roleList.vue')
		}, {
			path: 'resourcesList',
			name: '资源列表',
			meta: {
				equireAuth: false,
				title: '资源列表',
				icon: 'md-analytics'
			},
			component: () => import('@/view/auth/resourcesList.vue')
		}]
	}

]
