import { IArquivos } from 'app/shared/model/arquivos.model';
import { TipoRegistro } from 'app/shared/model/enumerations/tipo-registro.model';
import { TipoOperacao } from 'app/shared/model/enumerations/tipo-operacao.model';

export interface ILinhasArquivos {
  id?: number;
  nsr?: string;
  tipo?: string;
  dataPonto?: string;
  horaPonto?: string;
  dataAjustada?: string;
  horaAjustada?: string;
  pis?: string;
  nomeEmpregado?: string;
  linha?: string;
  tipoRegistro?: TipoRegistro;
  tipoOperacao?: TipoOperacao;
  arquivos?: IArquivos;
}

export const defaultValue: Readonly<ILinhasArquivos> = {};
