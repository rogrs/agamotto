import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './unidade-negocios.reducer';
import { IUnidadeNegocios } from 'app/shared/model/unidade-negocios.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IUnidadeNegociosUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const UnidadeNegociosUpdate = (props: IUnidadeNegociosUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { unidadeNegociosEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/unidade-negocios');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
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
        ...unidadeNegociosEntity,
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
          <h2 id="agamottoApp.unidadeNegocios.home.createOrEditLabel">
            <Translate contentKey="agamottoApp.unidadeNegocios.home.createOrEditLabel">Create or edit a UnidadeNegocios</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : unidadeNegociosEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="unidade-negocios-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="unidade-negocios-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="razaoSocialLabel" for="unidade-negocios-razaoSocial">
                  <Translate contentKey="agamottoApp.unidadeNegocios.razaoSocial">Razao Social</Translate>
                </Label>
                <AvField
                  id="unidade-negocios-razaoSocial"
                  type="text"
                  name="razaoSocial"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') }
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="nomeEmpresaLabel" for="unidade-negocios-nomeEmpresa">
                  <Translate contentKey="agamottoApp.unidadeNegocios.nomeEmpresa">Nome Empresa</Translate>
                </Label>
                <AvField
                  id="unidade-negocios-nomeEmpresa"
                  type="text"
                  name="nomeEmpresa"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') }
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="cnpjLabel" for="unidade-negocios-cnpj">
                  <Translate contentKey="agamottoApp.unidadeNegocios.cnpj">Cnpj</Translate>
                </Label>
                <AvField id="unidade-negocios-cnpj" type="text" name="cnpj" />
              </AvGroup>
              <AvGroup>
                <Label id="inscricaoEstadualLabel" for="unidade-negocios-inscricaoEstadual">
                  <Translate contentKey="agamottoApp.unidadeNegocios.inscricaoEstadual">Inscricao Estadual</Translate>
                </Label>
                <AvField id="unidade-negocios-inscricaoEstadual" type="text" name="inscricaoEstadual" />
              </AvGroup>
              <AvGroup>
                <Label id="empregadoraLabel" for="unidade-negocios-empregadora">
                  <Translate contentKey="agamottoApp.unidadeNegocios.empregadora">Empregadora</Translate>
                </Label>
                <AvInput
                  id="unidade-negocios-empregadora"
                  type="select"
                  className="form-control"
                  name="empregadora"
                  value={(!isNew && unidadeNegociosEntity.empregadora) || 'SIM'}
                >
                  <option value="SIM">{translate('agamottoApp.TipoBoolean.SIM')}</option>
                  <option value="NAO">{translate('agamottoApp.TipoBoolean.NAO')}</option>
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/unidade-negocios" replace color="info">
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
  unidadeNegociosEntity: storeState.unidadeNegocios.entity,
  loading: storeState.unidadeNegocios.loading,
  updating: storeState.unidadeNegocios.updating,
  updateSuccess: storeState.unidadeNegocios.updateSuccess
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(UnidadeNegociosUpdate);
