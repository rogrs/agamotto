import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IArquivos } from 'app/shared/model/arquivos.model';
import { getEntities as getArquivos } from 'app/entities/arquivos/arquivos.reducer';
import { getEntity, updateEntity, createEntity, reset } from './linhas-arquivos.reducer';
import { ILinhasArquivos } from 'app/shared/model/linhas-arquivos.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ILinhasArquivosUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ILinhasArquivosUpdateState {
  isNew: boolean;
  arquivosId: string;
}

export class LinhasArquivosUpdate extends React.Component<ILinhasArquivosUpdateProps, ILinhasArquivosUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      arquivosId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (!this.state.isNew) {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getArquivos();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { linhasArquivosEntity } = this.props;
      const entity = {
        ...linhasArquivosEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/linhas-arquivos');
  };

  render() {
    const { linhasArquivosEntity, arquivos, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="agamottoApp.linhasArquivos.home.createOrEditLabel">
              <Translate contentKey="agamottoApp.linhasArquivos.home.createOrEditLabel">Create or edit a LinhasArquivos</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : linhasArquivosEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="linhas-arquivos-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="linhas-arquivos-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nsrLabel" for="linhas-arquivos-nsr">
                    <Translate contentKey="agamottoApp.linhasArquivos.nsr">Nsr</Translate>
                  </Label>
                  <AvField id="linhas-arquivos-nsr" type="text" name="nsr" />
                </AvGroup>
                <AvGroup>
                  <Label id="tipoLabel" for="linhas-arquivos-tipo">
                    <Translate contentKey="agamottoApp.linhasArquivos.tipo">Tipo</Translate>
                  </Label>
                  <AvField id="linhas-arquivos-tipo" type="text" name="tipo" />
                </AvGroup>
                <AvGroup>
                  <Label id="dataPontoLabel" for="linhas-arquivos-dataPonto">
                    <Translate contentKey="agamottoApp.linhasArquivos.dataPonto">Data Ponto</Translate>
                  </Label>
                  <AvField id="linhas-arquivos-dataPonto" type="text" name="dataPonto" />
                </AvGroup>
                <AvGroup>
                  <Label id="horaPontoLabel" for="linhas-arquivos-horaPonto">
                    <Translate contentKey="agamottoApp.linhasArquivos.horaPonto">Hora Ponto</Translate>
                  </Label>
                  <AvField id="linhas-arquivos-horaPonto" type="text" name="horaPonto" />
                </AvGroup>
                <AvGroup>
                  <Label id="dataAjustadaLabel" for="linhas-arquivos-dataAjustada">
                    <Translate contentKey="agamottoApp.linhasArquivos.dataAjustada">Data Ajustada</Translate>
                  </Label>
                  <AvField id="linhas-arquivos-dataAjustada" type="text" name="dataAjustada" />
                </AvGroup>
                <AvGroup>
                  <Label id="horaAjustadaLabel" for="linhas-arquivos-horaAjustada">
                    <Translate contentKey="agamottoApp.linhasArquivos.horaAjustada">Hora Ajustada</Translate>
                  </Label>
                  <AvField id="linhas-arquivos-horaAjustada" type="text" name="horaAjustada" />
                </AvGroup>
                <AvGroup>
                  <Label id="pisLabel" for="linhas-arquivos-pis">
                    <Translate contentKey="agamottoApp.linhasArquivos.pis">Pis</Translate>
                  </Label>
                  <AvField id="linhas-arquivos-pis" type="text" name="pis" />
                </AvGroup>
                <AvGroup>
                  <Label id="nomeEmpregadoLabel" for="linhas-arquivos-nomeEmpregado">
                    <Translate contentKey="agamottoApp.linhasArquivos.nomeEmpregado">Nome Empregado</Translate>
                  </Label>
                  <AvField id="linhas-arquivos-nomeEmpregado" type="text" name="nomeEmpregado" />
                </AvGroup>
                <AvGroup>
                  <Label id="linhaLabel" for="linhas-arquivos-linha">
                    <Translate contentKey="agamottoApp.linhasArquivos.linha">Linha</Translate>
                  </Label>
                  <AvField id="linhas-arquivos-linha" type="text" name="linha" />
                </AvGroup>
                <AvGroup>
                  <Label id="tipoRegistroLabel" for="linhas-arquivos-tipoRegistro">
                    <Translate contentKey="agamottoApp.linhasArquivos.tipoRegistro">Tipo Registro</Translate>
                  </Label>
                  <AvInput
                    id="linhas-arquivos-tipoRegistro"
                    type="select"
                    className="form-control"
                    name="tipoRegistro"
                    value={(!isNew && linhasArquivosEntity.tipoRegistro) || 'CABECALHO'}
                  >
                    <option value="CABECALHO">{translate('agamottoApp.TipoRegistro.CABECALHO')}</option>
                    <option value="INCLUSAO_ALTERACAO">{translate('agamottoApp.TipoRegistro.INCLUSAO_ALTERACAO')}</option>
                    <option value="MARCACAO_PONTO">{translate('agamottoApp.TipoRegistro.MARCACAO_PONTO')}</option>
                    <option value="AJUSTE_TEMPO_REAL">{translate('agamottoApp.TipoRegistro.AJUSTE_TEMPO_REAL')}</option>
                    <option value="INCLUSAO_ALTERACAO_EXCLUSAO">{translate('agamottoApp.TipoRegistro.INCLUSAO_ALTERACAO_EXCLUSAO')}</option>
                    <option value="SEM_DEFINICAO">{translate('agamottoApp.TipoRegistro.SEM_DEFINICAO')}</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="tipoOperacaoLabel" for="linhas-arquivos-tipoOperacao">
                    <Translate contentKey="agamottoApp.linhasArquivos.tipoOperacao">Tipo Operacao</Translate>
                  </Label>
                  <AvInput
                    id="linhas-arquivos-tipoOperacao"
                    type="select"
                    className="form-control"
                    name="tipoOperacao"
                    value={(!isNew && linhasArquivosEntity.tipoOperacao) || 'INCLUSAO'}
                  >
                    <option value="INCLUSAO">{translate('agamottoApp.TipoOperacao.INCLUSAO')}</option>
                    <option value="ALTERACAO">{translate('agamottoApp.TipoOperacao.ALTERACAO')}</option>
                    <option value="MARCACAO_PONTO">{translate('agamottoApp.TipoOperacao.MARCACAO_PONTO')}</option>
                    <option value="CNPJ">{translate('agamottoApp.TipoOperacao.CNPJ')}</option>
                    <option value="CPF">{translate('agamottoApp.TipoOperacao.CPF')}</option>
                    <option value="SEM_DEFINICAO">{translate('agamottoApp.TipoOperacao.SEM_DEFINICAO')}</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="linhas-arquivos-arquivos">
                    <Translate contentKey="agamottoApp.linhasArquivos.arquivos">Arquivos</Translate>
                  </Label>
                  <AvInput id="linhas-arquivos-arquivos" type="select" className="form-control" name="arquivos.id">
                    <option value="" key="0" />
                    {arquivos
                      ? arquivos.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.nome}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/linhas-arquivos" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  arquivos: storeState.arquivos.entities,
  linhasArquivosEntity: storeState.linhasArquivos.entity,
  loading: storeState.linhasArquivos.loading,
  updating: storeState.linhasArquivos.updating,
  updateSuccess: storeState.linhasArquivos.updateSuccess
});

const mapDispatchToProps = {
  getArquivos,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(LinhasArquivosUpdate);
