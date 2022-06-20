import axios from '@/libs/api.request'

// 处理后台请求接口方法
export const getRouterReq = (access) => {
  return axios.request({
    url: 'get_router',
    params: {
      access
    },
    config:{
      headers:{
        Authorization:'JWT'+ window.Cookies.getItem('token')
      }
    },
    method: 'get'
  })
}
