import {Device} from "./Device";

export class Consumption {
  id: string | undefined;
  timestamp: string = "";
  hourlyConsumption: number = 0;
  deviceDto: Device = new Device();
}
