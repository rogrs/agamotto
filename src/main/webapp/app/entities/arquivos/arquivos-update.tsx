import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './arquivos.reducer';
import { IArquivos } from 'app/shared/model/arquivos.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IArquivosUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ArquivosUpdate = (props: IArquivosUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { arquivosEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/arquivos');
  };

  useEffect(() => {
    if (!isNew) {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...arquivosEntity,
        ...values
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="agamottoApp.arquivos.home.createOrEditLabel">
            <Translate contentKey="agamottoApp.arquivos.home.createOrEditLabel">Create or edit a Arquivos</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : arquivosEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="arquivos-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="arquivos-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nomeLabel" for="arquivos-nome">
                  <Translate contentKey="agamottoApp.arquivos.nome">Nome</Translate>
                </Label>
                <AvField
                  id="arquivos-nome"
                  type="text"
                  name="nome"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') }
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="caminhoLabel" for="arquivos-caminho">
                  <Translate contentKey="agamottoApp.arquivos.caminho">Caminho</Translate>
                </Label>
                <AvField
                  id="arquivos-caminho"
                  type="text"
                  name="caminho"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') }
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="md5Label" for="arquivos-md5">
                  <Translate contentKey="agamottoApp.arquivos.md5">Md 5</Translate>
                </Label>
                <AvField
                  id="arquivos-md5"
                  type="text"
                  name="md5"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') }
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="statusLabel" for="arquivos-status">
                  <Translate contentKey="agamottoApp.arquivos.status">Status</Translate>
                </Label>
                <AvInput
                  id="arquivos-status"
                  type="select"
                  className="form-control"
                  name="status"
                  value={(!isNew && arquivosEntity.status) || 'CRIADO'}
                >
                  <option value="CRIADO">{translate('agamottoApp.StatusArquivo.CRIADO')}</option>
                  <option value="ERRO">{translate('agamottoApp.StatusArquivo.ERRO')}</option>
                  <option value="PROCESSADO">{translate('agamottoApp.StatusArquivo.PROCESSADO')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="tipoLabel" for="arquivos-tipo">
                  <Translate contentKey="agamottoApp.arquivos.tipo">Tipo</Translate>
                </Label>
                <AvField id="arquivos-tipo" type="text" name="tipo" />
              </AvGroup>
              <AvGroup>
                <Label id="totalLinhasLabel" for="arquivos-totalLinhas">
                  <Translate contentKey="agamottoApp.arquivos.totalLinhas">Total Linhas</Translate>
                </Label>
                <AvField id="arquivos-totalLinhas" type="string" className="form-control" name="totalLinhas" />
              </AvGroup>
              <AvGroup>
                <Label id="criacaoLabel" for="arquivos-criacao">
                  <Translate contentKey="agamottoApp.arquivos.criacao">Criacao</Translate>
                </Label>
                <AvField id="arquivos-criacao" type="date" className="form-control" name="criacao" />
              </AvGroup>
              <AvGroup>
                <Label id="processamentoLabel" for="arquivos-processamento">
                  <Translate contentKey="agamottoApp.arquivos.processamento">Processamento</Translate>
                </Label>
                <AvField id="arquivos-processamento" type="date" className="form-control" name="processamento" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/arquivos" replace color="info">
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
};

const mapStateToProps = (storeState: IRootState) => ({
  arquivosEntity: storeState.arquivos.entity,
  loading: storeState.arquivos.loading,
  updating: storeState.arquivos.updating,
  updateSuccess: storeState.arquivos.updateSuccess
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ArquivosUpdate);
