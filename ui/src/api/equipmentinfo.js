import request from '@/utils/request'

export function getEquipmentInfo(data) {
  return request({
    url: '/laboratory/equipmentinfo',
    method: 'get',
     params: { data }
  })
}
export function newEquipmentInfo(data) {
  return request({
    url: '/laboratory/equipmentinfo',
    method: 'post',
    data
  })
}
export function updateEquipmentInfo(data) {
  return request({
    url: '/laboratory/equipmentinfo',
    method: 'put',
    data
  })
}

export function deleteEquipmentInfo(data) {
  return request({
    url: '/laboratory/equipmentinfo',
    method: 'delete',
    data
  })
}
