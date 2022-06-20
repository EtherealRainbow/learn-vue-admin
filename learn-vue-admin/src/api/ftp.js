import axios from '@/libs/api.request'
// 查询FTP链接信息
export const getFtpConnectionInfoList = (formData) => {
	return axios.request({
		url: 'ftpConnectionInfo/getFtpConnectionInfoList',
		params: formData,
		method: 'get'
	})
}

// 新增
export const addFtpFileInfo = (formData) => {
	return axios.request({
		url: 'ftpFileInfo/addFtpFileInfo',
		params: formData,
		method: 'get'
	})
}
//查询ftp子文件
export const getFTPChildren = (formData) => {
	return axios.request({
		url: 'ftpFileInfo/getFtpFileInfoList',
		params: formData,
		method: 'get'
	})
}

//重命名
export const updateFtpFileInfo = (formData) => {
	return axios.request({
		url: 'ftpFileInfo/updateFtpFileInfo',
		params: formData,
		method: 'get'
	})
}
//删除
export const deleteFtpFileInfo = (formData) => {
	return axios.request({
		url: 'ftpFileInfo/deleteFtpFileInfo',
		params: formData,
		method: 'get'
	})
}

//下载
export const downloadFile = (formData) => {
	return axios.request({
		url: 'ftpFileInfo/downloadFile',
		params: formData,
		method: 'get'
	})
}


//添加
export const addFtpConnectionInfo = (formData) => {
	return axios.request({
		url: 'ftpConnectionInfo/addFtpConnectionInfo',
		params: formData,
		method: 'post'
	})
}

//删除
export const deleteFtpConnectionInfo = (formData) => {
	return axios.request({
		url: 'ftpConnectionInfo/deleteFtpConnectionInfo',
		params: formData,
		method: 'post'
	})
}

//修改
export const updateFtpConnectionInfo = (formData) => {
	return axios.request({
		url: 'ftpConnectionInfo/updateFtpConnectionInfo',
		params: formData,
		method: 'post'
	})
}

