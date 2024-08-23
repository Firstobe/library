import request from '@/utils/request'

export function getBorrowRecord(data) {
  return request({
    url: '/laboratory/borrowRecord',
    method: 'get',
    params:  data
  })
}
export function newBorrowRecord(data) {
  return request({
    url: '/laboratory/borrowRecord',
    method: 'post',
    data
  })
}
export function updateBorrowRecord(data) {
  return request({
    url: '/laboratory/borrowRecord',
    method: 'put',
    data
  })
}

export function deleteBorrowRecord(data) {
  return request({
    url: '/laboratory/borrowRecord',
    method: 'delete',
    data
  })
}
export function returnBorrowRecord(data) {
  return request({
    url: '/laboratory/borrowRecord/return',
    method: 'put',
    data
  })
}
