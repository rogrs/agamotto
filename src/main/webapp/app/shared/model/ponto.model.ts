import { IControlePonto } from 'app/shared/model/controle-ponto.model';
import { TipoMotivoAjuste } from 'app/shared/model/enumerations/tipo-motivo-ajuste.model';

export interface IPonto {
  id?: number;
  marcacao?: number;
  motivo?: TipoMotivoAjuste;
  controlePonto?: IControlePonto;
}

export const defaultValue: Readonly<IPonto> = {};
