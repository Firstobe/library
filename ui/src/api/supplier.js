import request from '@/utils/request'

export function getSupplier(data) {
  return request({
    url: '/laboratory/supplier',
    method: 'get',
    params: { data }
  })
}
export function newSupplier(data) {
  return request({
    url: '/laboratory/supplier',
    method: 'post',
    data
  })
}
export function updateSupplier(data) {
  return request({
    url: '/laboratory/supplier',
    method: 'put',
    data
  })
}

export function deleteSupplier(data) {
  return request({
    url: '/laboratory/supplier',
    method: 'delete',
    data
  })
}
