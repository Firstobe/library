import request from '@/utils/request'

export function getBookInfoPage(data) {
  return request({
    url: '/api/library-service/bookInfo',
    method: 'get',
    params: data
  })
}
export function newBookInfo(data) {
  return request({
    url: '/api/library-service/bookInfo',
    method: 'post',
    data
  })
}
export function updateBookInfo(data) {
  return request({
    url: '/api/library-service/bookInfo',
    method: 'put',
    data
  })
}

export function deleteBookInfo(data) {
  return request({
    url: '/api/library-service/bookInfo',
    method: 'delete',
    data
  })
}
