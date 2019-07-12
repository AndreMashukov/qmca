export interface ICalibration {
  id?: number;
  name?: string;
  regionFile?: string;
}

export class Calibration implements ICalibration {
  constructor(public id?: number, public name?: string, public regionFile?: string) {}
}
