package com.dts.classes.Transacciones.Recepcion.Reglas_recepcion;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.simpleframework.xml.ElementList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class clsBeReglas_recepcionList {
    @ElementList(inline=true,required = false)
    public List<clsBeReglas_recepcion> items = new List<clsBeReglas_recepcion>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(@Nullable Object o) {
            return false;
        }

        @NonNull
        @Override
        public Iterator<clsBeReglas_recepcion> iterator() {
            return null;
        }

        @Nullable
        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(@Nullable T[] a) {
            return null;
        }

        @Override
        public boolean add(clsBeReglas_recepcion clsBeReglas_recepcion) {
            return false;
        }

        @Override
        public boolean remove(@Nullable Object o) {
            return false;
        }

        @Override
        public boolean containsAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(@NonNull Collection<? extends clsBeReglas_recepcion> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, @NonNull Collection<? extends clsBeReglas_recepcion> c) {
            return false;
        }

        @Override
        public boolean removeAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public boolean equals(@Nullable Object o) {
            return false;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public clsBeReglas_recepcion get(int index) {
            return null;
        }

        @Override
        public clsBeReglas_recepcion set(int index, clsBeReglas_recepcion element) {
            return null;
        }

        @Override
        public void add(int index, clsBeReglas_recepcion element) {

        }

        @Override
        public clsBeReglas_recepcion remove(int index) {
            return null;
        }

        @Override
        public int indexOf(@Nullable Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(@Nullable Object o) {
            return 0;
        }

        @NonNull
        @Override
        public ListIterator<clsBeReglas_recepcion> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<clsBeReglas_recepcion> listIterator(int index) {
            return null;
        }

        @NonNull
        @Override
        public List<clsBeReglas_recepcion> subList(int fromIndex, int toIndex) {
            return null;
        }
    };
}