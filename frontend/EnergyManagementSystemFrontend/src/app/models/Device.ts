
export class Device {
  id: string | undefined;
  description: string = "";
  address: string = "";
  hourlyEnergyConsumptionLimit: number = 0;
  ownerId: string = "";
}

export interface DeviceDetails {
  id:string | undefined;
  description:string;
  address: string;
  hourlyEnergyConsumptionLimit:number;
  ownerId:string;
  isEdit:boolean;
}

export const DeviceColumns = [
  {
    key: 'id',
    type: 'text',
    label: 'id',
    required: true,
  },
  {
    key: 'description',
    type: 'text',
    label: 'description',
    required: true,
  },
  {
    key: 'address',
    type: 'text',
    label: 'address',
    required: true,
  },
  {
    key: 'hourlyEnergyConsumptionLimit',
    type: 'number',
    label: 'hourlyEnergyConsumptionLimit',
    required: true,
  },
  {
    key: 'ownerId',
    type: 'text',
    label: 'ownerId',
    required: true,
  },
  {
    key: 'isEdit',
    type: 'isEdit',
    label: '',
  },
];
