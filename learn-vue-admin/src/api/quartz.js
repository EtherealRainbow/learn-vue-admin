import axios from '@/libs/api.request'

// 查询FTP链接信息
export const testPdf = (formData) => {
    return axios.request({
        url: 'testPdf',
        params: formData,
        method: 'get'
    })
}
