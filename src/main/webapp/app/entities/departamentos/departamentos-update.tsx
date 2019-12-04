import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IUnidadeNegocios } from 'app/shared/model/unidade-negocios.model';
import { getEntities as getUnidadeNegocios } from 'app/entities/unidade-negocios/unidade-negocios.reducer';
import { getEntity, updateEntity, createEntity, reset } from './departamentos.reducer';
import { IDepartamentos } from 'app/shared/model/departamentos.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IDepartamentosUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IDepartamentosUpdateState {
  isNew: boolean;
  unidadeNegociosId: string;
}

export class DepartamentosUpdate extends React.Component<IDepartamentosUpdateProps, IDepartamentosUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      unidadeNegociosId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getUnidadeNegocios();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { departamentosEntity } = this.props;
      const entity = {
        ...departamentosEntity,
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
    this.props.history.push('/departamentos');
  };

  render() {
    const { departamentosEntity, unidadeNegocios, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="agamottoApp.departamentos.home.createOrEditLabel">
              <Translate contentKey="agamottoApp.departamentos.home.createOrEditLabel">Create or edit a Departamentos</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : departamentosEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="departamentos-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="departamentos-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nomeLabel" for="departamentos-nome">
                    <Translate contentKey="agamottoApp.departamentos.nome">Nome</Translate>
                  </Label>
                  <AvField
                    id="departamentos-nome"
                    type="text"
                    name="nome"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="departamentos-unidadeNegocios">
                    <Translate contentKey="agamottoApp.departamentos.unidadeNegocios">Unidade Negocios</Translate>
                  </Label>
                  <AvInput id="departamentos-unidadeNegocios" type="select" className="form-control" name="unidadeNegocios.id">
                    <option value="" key="0" />
                    {unidadeNegocios
                      ? unidadeNegocios.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.razaoSocial}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/departamentos" replace color="info">
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
  unidadeNegocios: storeState.unidadeNegocios.entities,
  departamentosEntity: storeState.departamentos.entity,
  loading: storeState.departamentos.loading,
  updating: storeState.departamentos.updating,
  updateSuccess: storeState.departamentos.updateSuccess
});

const mapDispatchToProps = {
  getUnidadeNegocios,
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
)(DepartamentosUpdate);
